package com.hiddenswitch.proto3.net.amazon;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.hiddenswitch.proto3.net.models.LoginToken;

import java.util.List;

@DynamoDBTable(tableName = "authorizations")
public class AuthorizationRecord {
	private String userId;
	private List<HashedLoginToken> loginTokens;
	private String scrypt;

	@DynamoDBHashKey
	public String getUserId() {
		return userId;
	}

	@DynamoDBAttribute
	public String getScrypt() {
		return scrypt;
	}

	public void setScrypt(String scrypt) {
		this.scrypt = scrypt;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@DynamoDBAttribute
	public List<HashedLoginToken> getLoginTokens() {
		return loginTokens;
	}

	public void setLoginTokens(List<HashedLoginToken> loginTokens) {
		this.loginTokens = loginTokens;
	}
}
