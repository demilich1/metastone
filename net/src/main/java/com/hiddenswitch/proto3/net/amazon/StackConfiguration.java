package com.hiddenswitch.proto3.net.amazon;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.sqs.AmazonSQSClient;

public class StackConfiguration {
	public AWSCredentials credentials;
	public AmazonSQSClient queue;
	public AmazonDynamoDB dynamoDBClient;
	public DynamoDBMapper database;
}
