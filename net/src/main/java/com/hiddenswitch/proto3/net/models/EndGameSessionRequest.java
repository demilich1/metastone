package com.hiddenswitch.proto3.net.models;

import java.io.Serializable;

/**
 * Created by bberman on 12/6/16.
 */
public class EndGameSessionRequest implements Serializable {
	private String gameId;

	public EndGameSessionRequest() {
	}

	public EndGameSessionRequest(String gameId) {
		setGameId(gameId);
	}

	public EndGameSessionRequest withGameId(String gameId) {
		setGameId(gameId);
		return this;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
}
