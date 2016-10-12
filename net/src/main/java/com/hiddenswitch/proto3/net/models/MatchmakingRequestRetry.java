package com.hiddenswitch.proto3.net.models;

public class MatchmakingRequestRetry {
	private String receipt;
	private String gameId;
	public int delayMilliseconds;

	public MatchmakingRequestRetry(String receipt, String gameId) {
		setReceipt(receipt);
		setGameId(gameId);
	}

	public String getReceipt() {
		return receipt;
	}

	private void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getGameId() {
		return gameId;
	}

	private void setGameId(String gameId) {
		this.gameId = gameId;
	}
}
