package com.hiddenswitch.proto3.net.amazon;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.hiddenswitch.proto3.net.models.LoginToken;
import com.lambdaworks.crypto.SCryptUtil;

import java.util.Date;

@DynamoDBDocument
public class HashedLoginToken {
	public String hashedLoginToken;
	public Date expiresAt;

	public HashedLoginToken(LoginToken token) {
		hashedLoginToken = SCryptUtil.scrypt(token.token, 256, 4, 1);
		expiresAt = token.expiresAt;
	}
}
