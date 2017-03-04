package com.hiddenswitch.proto3.net.impl.server;

import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;

public abstract class GameSession {

	/**
	 * Returns the information the client needs to know whom to connect to and what message to send.
	 *
	 * @return {ClientConnectionConfiguration} Data for player 1.
	 */
	public abstract ClientConnectionConfiguration getConfigurationForPlayer1();

	/**
	 * Returns the information the client needs to know whom to connect to and what message to send.
	 *
	 * @return {ClientConnectionConfiguration} Data for player 2.
	 */
	public abstract ClientConnectionConfiguration getConfigurationForPlayer2();

	/**
	 * A default constructor for serialization purposes.
	 */
	public GameSession() {
	}
	/**
	 * An identifier the matchmaking API can use to identify this particular game session for management tasks, like
	 * cleaning up a game (closing its sockets) or sending in-game messages.
	 *
	 * @return The ID
	 */
	public abstract String getGameId();
}
