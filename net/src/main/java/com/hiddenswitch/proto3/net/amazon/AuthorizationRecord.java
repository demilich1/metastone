package com.hiddenswitch.proto3.net.amazon;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.hiddenswitch.proto3.net.models.LoginToken;

@DynamoDBTable(tableName = "authorizations")
public class AuthorizationRecord {
	private String userId;
	private HashedLoginToken[] loginTokens = new HashedLoginToken[] {};
	private String scrypt;

	@DynamoDBAttribute
	public String getScrypt() {
		return scrypt;
	}

	public void setScrypt(String scrypt) {
		this.scrypt = scrypt;
	}

	@DynamoDBHashKey
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@DynamoDBAttribute
	public HashedLoginToken[] getLoginTokens() {
		return loginTokens;
	}

	public void setLoginTokens(HashedLoginToken[] loginTokens) {
		this.loginTokens = loginTokens;
	}
}
