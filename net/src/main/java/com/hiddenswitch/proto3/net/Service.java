package com.hiddenswitch.proto3.net;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.sqs.AmazonSQSClient;
import io.vertx.core.AbstractVerticle;

public abstract class Service <T extends Service<T>> extends AbstractVerticle {
	private DynamoDBMapper database;
	private AWSCredentials credentials;
	private AmazonSQSClient queue;

	@SuppressWarnings("unchecked")
	public T withProductionConfiguration() {
		setCredentials(getAWSCredentials());
		setDatabase(new DynamoDBMapper(new AmazonDynamoDBClient(getCredentials())));
		setQueue(new AmazonSQSClient(getCredentials()));
		return (T) this;
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
		return (T)this;
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
		return (T)this;
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
		return (T)this;
	}
}
