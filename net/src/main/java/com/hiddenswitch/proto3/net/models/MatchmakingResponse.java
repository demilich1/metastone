package com.hiddenswitch.proto3.net.models;

import net.demilich.metastone.game.GameContext;

public class MatchmakingResponse {
	private MatchmakingRequestRetry retry;
	private String myChannelId;
	private Game game;
	private String gameId;

	public MatchmakingRequestRetry getRetry() {
		return retry;
	}

	public void setRetry(MatchmakingRequestRetry retry) {
		this.retry = retry;
	}

	public String getMyChannelId() {
		return myChannelId;
	}

	public void setMyChannelId(String myChannelId) {
		this.myChannelId = myChannelId;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
}
