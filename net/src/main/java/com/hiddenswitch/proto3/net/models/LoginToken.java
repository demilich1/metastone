package com.hiddenswitch.proto3.net.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import java.util.Date;

@DynamoDBDocument
public class LoginToken {
	public Date expiresAt;
	public String token;
}
