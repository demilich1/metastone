package com.hiddenswitch.proto3.net;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.hiddenswitch.proto3.net.models.PlayerProfile;


public class Accounts {
	public String getUserId() {
		// Gets the current user ID for the request.
		return "";
	}

	public PlayerProfile getProfileForId(String userId) {
		return null;
	}

	public AWSCredentials getAWSCredentials() {
		DefaultAWSCredentialsProviderChain chain = new DefaultAWSCredentialsProviderChain();
		return chain.getCredentials();
	}
}
