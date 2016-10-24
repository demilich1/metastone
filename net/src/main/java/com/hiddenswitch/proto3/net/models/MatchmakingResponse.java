package com.hiddenswitch.proto3.net.models;

import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;

public class MatchmakingResponse {
	private MatchmakingRequestRetry retry;
	private ClientConnectionConfiguration connection;

	public MatchmakingRequestRetry getRetry() {
		return retry;
	}

	public void setRetry(MatchmakingRequestRetry retry) {
		this.retry = retry;
	}

	public ClientConnectionConfiguration getConnection() {
		return connection;
	}

	public void setConnection(ClientConnectionConfiguration connection) {
		this.connection = connection;
	}
}
