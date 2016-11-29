package com.hiddenswitch.proto3.net;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.hiddenswitch.proto3.net.amazon.AwsStack;
import com.hiddenswitch.proto3.net.amazon.AwsStackConfiguration;
import io.vertx.core.AbstractVerticle;
import org.elasticmq.rest.sqs.SQSRestServer;
import org.elasticmq.rest.sqs.SQSRestServerBuilder;

public abstract class Service<T extends Service<T>> extends AbstractVerticle {
	private DynamoDBMapper database;
	private AWSCredentials credentials;
	private AmazonSQSClient queue;
	private static AmazonDynamoDB dynamoDBEmbedded;
	private static SQSRestServer elasticMQ;
	private static boolean embeddedConfigured;

	@SuppressWarnings("unchecked")
	public T withProductionConfiguration() {
		setCredentials(getAWSCredentials());
		setDatabase(new DynamoDBMapper(new AmazonDynamoDBClient(getCredentials())));
		setQueue(new AmazonSQSClient(getCredentials()));
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withEmbeddedConfiguration() {
		createdEmbeddedServices();

		this.credentials = new BasicAWSCredentials("x", "y");
		this.database = new DynamoDBMapper(dynamoDBEmbedded);
		this.queue = new AmazonSQSClient(credentials);
		this.queue.setEndpoint("http://localhost:9324");

		return (T) this;
	}

	private synchronized static void createdEmbeddedServices() {
		if (elasticMQ == null) {
			elasticMQ = SQSRestServerBuilder.start();
			elasticMQ.waitUntilStarted();
		}

		if (dynamoDBEmbedded == null) {
			dynamoDBEmbedded = DynamoDBEmbedded.create().amazonDynamoDB();
		}

		if (embeddedConfigured) {
			return;
		}

		AwsStackConfiguration configuration = new AwsStackConfiguration();
		BasicAWSCredentials credentials = new BasicAWSCredentials("x", "y");
		configuration.credentials = credentials;
		configuration.dynamoDBClient = dynamoDBEmbedded;
		configuration.database = new DynamoDBMapper(dynamoDBEmbedded);
		configuration.queue = new AmazonSQSClient(credentials).withEndpoint("http://localhost:9324");
		AwsStack.initializeStack(configuration);

		embeddedConfigured = true;
	}

	private AWSCredentials getAWSCredentials() {
		DefaultAWSCredentialsProviderChain chain = new DefaultAWSCredentialsProviderChain();
		return chain.getCredentials();
	}

	public AWSCredentials getCredentials() {
		return credentials;
	}

	public void setCredentials(AWSCredentials credentials) {
		this.credentials = credentials;
	}

	@SuppressWarnings("unchecked")
	public T withCredentials(AWSCredentials credentials) {
		setCredentials(credentials);
		return (T) this;
	}

	public AmazonSQSClient getQueue() {
		return queue;
	}

	public void setQueue(AmazonSQSClient queue) {
		this.queue = queue;
	}

	@SuppressWarnings("unchecked")
	public T withQueue(AmazonSQSClient queue) {
		setQueue(queue);
		return (T) this;
	}

	public DynamoDBMapper getDatabase() {
		return database;
	}

	public void setDatabase(DynamoDBMapper database) {
		this.database = database;
	}

	@SuppressWarnings("unchecked")
	public T withDatabase(DynamoDBMapper database) {
		setDatabase(database);
		return (T) this;
	}

	@Override
	public void stop() {
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
