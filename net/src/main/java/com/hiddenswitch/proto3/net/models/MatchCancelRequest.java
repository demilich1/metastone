package com.hiddenswitch.proto3.net.models;

import java.io.Serializable;

public class MatchCancelRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String userId;

	public MatchCancelRequest(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
}
