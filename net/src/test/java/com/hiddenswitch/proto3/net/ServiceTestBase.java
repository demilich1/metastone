package com.hiddenswitch.proto3.net;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.hiddenswitch.proto3.net.amazon.AwsStack;
import com.hiddenswitch.proto3.net.amazon.AwsStackConfiguration;
import org.elasticmq.rest.sqs.SQSRestServer;
import org.elasticmq.rest.sqs.SQSRestServerBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class ServiceTestBase<T extends Service<T>> {
	protected T service;
	private AmazonSQSClient queue;
	private DynamoDBMapper database;
	private AmazonDynamoDB dynamoDBEmbedded;
	private SQSRestServer elasticMQ;

	public abstract T setupAndReturnServiceInstance();

	@BeforeMethod
	public void setUp() throws Exception {
		AwsStackConfiguration configuration = new AwsStackConfiguration();
		BasicAWSCredentials credentials = new BasicAWSCredentials("x", "y");
		try {
			elasticMQ = SQSRestServerBuilder.start();
			elasticMQ.waitUntilStarted();
			dynamoDBEmbedded = DynamoDBEmbedded.create().amazonDynamoDB();
			configuration.credentials = credentials;
			database = new DynamoDBMapper(dynamoDBEmbedded);
			queue = new AmazonSQSClient(credentials);
			queue.setEndpoint("http://localhost:9324");
		} catch (Exception ignored) {
		} finally {
			configuration.dynamoDBClient = dynamoDBEmbedded;
			configuration.database = database;
			configuration.queue = queue;
			AwsStack.initializeStack(configuration);
			service = setupAndReturnServiceInstance()
					.withCredentials(credentials)
					.withDatabase(database)
					.withQueue(queue);
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		try {
			if (elasticMQ != null) {
				elasticMQ.stopAndWait();
			}
			if (dynamoDBEmbedded != null) {
				dynamoDBEmbedded.shutdown();
			}
			if (queue != null) {
				queue.shutdown();
			}
		} catch (Exception ignored) {
		}
	}
}
