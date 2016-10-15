package com.hiddenswitch.proto3.net.amazon;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedTimestamp;
import com.hiddenswitch.proto3.net.models.LoginToken;
import com.lambdaworks.crypto.SCryptUtil;

import java.util.Date;

@DynamoDBDocument
public class HashedLoginToken {
	private String hashedLoginToken;
	private Date expiresAt;

	public HashedLoginToken() {
	}

	public HashedLoginToken(LoginToken token) {
		setHashedLoginToken(SCryptUtil.scrypt(token.token, 256, 4, 1));
		setExpiresAt(token.expiresAt);
	}

	@DynamoDBAttribute
	public String getHashedLoginToken() {
		return hashedLoginToken;
	}

	public void setHashedLoginToken(String hashedLoginToken) {
		this.hashedLoginToken = hashedLoginToken;
	}

	@DynamoDBAttribute
	@DynamoDBTypeConvertedTimestamp
	public Date getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Date expiresAt) {
		this.expiresAt = expiresAt;
	}
}
