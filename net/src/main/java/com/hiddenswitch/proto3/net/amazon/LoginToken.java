package com.hiddenswitch.proto3.net.amazon;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@DynamoDBDocument
public class LoginToken {
	public Date expiresAt;
	public String token;

	public static LoginToken createSecure() {
		SecureRandom random = new SecureRandom();
		return new LoginToken(Date.from(Instant.now().plus(60L * 60L * 24L * 365L, ChronoUnit.SECONDS)), new BigInteger(130, random).toString(32));
	}

	public LoginToken() {
	}

	public LoginToken(String token) {
		this.expiresAt = Date.from(Instant.MAX);
		this.token = token;
	}

	public LoginToken(Date expiresAt, String token) {
		this.expiresAt = expiresAt;
		this.token = token;
	}
}
