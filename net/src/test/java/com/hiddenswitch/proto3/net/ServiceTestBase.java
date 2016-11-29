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

	public abstract T setupAndReturnServiceInstance();

	@BeforeMethod
	public void setUp() throws Exception {
		service = setupAndReturnServiceInstance().withEmbeddedConfiguration();
	}
}
