package com.hiddenswitch.proto3.net.models;

import com.hiddenswitch.proto3.server.PregamePlayerConfiguration;
import com.hiddenswitch.proto3.server.SocketServerImpl;

import java.io.Serializable;

public class CreateGameSessionRequest implements Serializable {
	private String gameId;
	private PregamePlayerConfiguration pregame1;
	private PregamePlayerConfiguration pregame2;
	private long noActivityTimeout = SocketServerImpl.DEFAULT_NO_ACTIVITY_TIMEOUT;

	public PregamePlayerConfiguration getPregame1() {
		return pregame1;
	}

	public void setPregame1(PregamePlayerConfiguration pregame1) {
		this.pregame1 = pregame1;
	}

	public PregamePlayerConfiguration getPregame2() {
		return pregame2;
	}

	public void setPregame2(PregamePlayerConfiguration pregame2) {
		this.pregame2 = pregame2;
	}

	public CreateGameSessionRequest withPregame1(PregamePlayerConfiguration pregame1) {
		setPregame1(pregame1);
		return this;
	}

	public CreateGameSessionRequest withPregame2(PregamePlayerConfiguration pregame2) {
		setPregame2(pregame2);
		return this;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	/**
	 * Gets how many milliseconds to wait for activity before shutting down this game session.
	 *
	 * @return Milliseconds
	 */
	public long getNoActivityTimeout() {
		return noActivityTimeout;
	}

	/**
	 * Sets the number of milliseconds to wait for activity before shutting down this game session.
	 *
	 * @param noActivityTimeout Milliseconds
	 */
	public void setNoActivityTimeout(long noActivityTimeout) {
		this.noActivityTimeout = noActivityTimeout;
	}

	/**
	 * Sets the number of milliseconds to wait for activity before shutting down this game session.
	 *
	 * @param noActivityTimeout Milliseconds
	 * @return The request
	 */
	public CreateGameSessionRequest withNoActivityTimeout(long noActivityTimeout) {
		setNoActivityTimeout(noActivityTimeout);
		return this;
	}

	public CreateGameSessionRequest withGameId(String gameId) {
		setGameId(gameId);
		return this;
	}
}
