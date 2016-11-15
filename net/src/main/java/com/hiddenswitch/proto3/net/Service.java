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
	}

	private void initializeProductionDependencies() {
		if (credentials == null) {
			setCredentials(getAWSCredentials());
		}
		if (database == null) {
			setDatabase(new DynamoDBMapper(new AmazonDynamoDBClient(getCredentials())));
		}
		if (queue == null) {
			setQueue(new AmazonSQSClient(getCredentials()));
		}
	}

	private AWSCredentials getAWSCredentials() {
		DefaultAWSCredentialsProviderChain chain = new DefaultAWSCredentialsProviderChain();
		return chain.getCredentials();
	}

	public AWSCredentials getCredentials() {
		if (credentials == null) {
			initializeProductionDependencies();
		}
		return credentials;
	}

	public void setCredentials(AWSCredentials credentials) {
		this.credentials = credentials;
	}

	public AmazonSQSClient getQueue() {
		if (queue == null) {
			initializeProductionDependencies();
		}
		return queue;
	}

	public void setQueue(AmazonSQSClient queue) {
		this.queue = queue;
	}

	public DynamoDBMapper getDatabase() {
		if (database == null) {
			initializeProductionDependencies();
		}
		return database;
	}

	public void setDatabase(DynamoDBMapper database) {
		this.database = database;
	}
}
