package com.hiddenswitch.proto3.net.amazon;

public class MatchmakingRequestMessage {
	public String getGameId() {
		return gameId;
	}

	public MatchmakingRequestMessage(String gameId) {
		this.gameId = gameId;
	}

	private String gameId;
}
