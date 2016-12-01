package com.hiddenswitch.proto3.net.common;

import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.common.MatchmakingRequest;

public class MatchmakingResponse {
	private ClientConnectionConfiguration connection;

	private MatchmakingRequest retry;

	public ClientConnectionConfiguration getConnection() {
		return connection;
	}

	public void setConnection(ClientConnectionConfiguration connection) {
		this.connection = connection;
	}

	public MatchmakingRequest getRetry() {
		return retry;
	}

	public void setRetry(MatchmakingRequest retry) {
		this.retry = retry;
	}
}
