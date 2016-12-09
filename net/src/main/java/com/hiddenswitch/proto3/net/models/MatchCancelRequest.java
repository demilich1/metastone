package com.hiddenswitch.proto3.net.models;

import java.io.Serializable;

public class MatchCancelRequest implements Serializable {
	private final String userId;

	public MatchCancelRequest(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
}
