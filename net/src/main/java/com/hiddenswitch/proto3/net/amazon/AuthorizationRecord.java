package com.hiddenswitch.proto3.net.amazon;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.hiddenswitch.proto3.net.models.LoginToken;

@DynamoDBTable(tableName = "authorizations")
public class AuthorizationRecord {
	@DynamoDBHashKey
	private String userId;
	@DynamoDBAttribute
	private LoginToken[] loginTokens;
	private String scrypt;

	@DynamoDBAttribute
	public String getScrypt() {
		return scrypt;
	}

	public void setScrypt(String scrypt) {
		this.scrypt = scrypt;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LoginToken[] getLoginTokens() {
		return loginTokens;
	}

	public void setLoginTokens(LoginToken[] loginTokens) {
		this.loginTokens = loginTokens;
	}
}
