package com.hiddenswitch.proto3.net;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.sqs.AmazonSQSClient;

public class Service {
	private DynamoDBMapper database;
	private AWSCredentials credentials;
	private AmazonSQSClient queue;

	public Service() {
		setCredentials(getAWSCredentials());
		setDatabase(new DynamoDBMapper(new AmazonDynamoDBClient(getCredentials())));
		setQueue(new AmazonSQSClient(getCredentials()));
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

	public AmazonSQSClient getQueue() {
		return queue;
	}

	public void setQueue(AmazonSQSClient queue) {
		this.queue = queue;
	}

	public DynamoDBMapper getDatabase() {
		return database;
	}

	public void setDatabase(DynamoDBMapper database) {
		this.database = database;
	}
}
