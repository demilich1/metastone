package com.hiddenswitch.proto3.net.amazon;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.hiddenswitch.proto3.net.models.User;

@DynamoDBTable(tableName = "users")
public class UserRecord {
	@DynamoDBHashKey
	public String id;
	@DynamoDBAttribute
	public User user;
}
