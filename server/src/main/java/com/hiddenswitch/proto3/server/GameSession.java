package com.hiddenswitch.proto3.server;

import com.hiddenswitch.proto3.common.ClientConnectionConfiguration;
import org.apache.commons.lang3.RandomStringUtils;

public abstract class GameSession {
	private final String id;

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
	 * Creates a new game session for the given two players. Once constructed, the session should
	 * be connectible from the clients given the ClientConnectionConfiguration provided by the API.
	 *
	 * @param player1 The first player
	 * @param player2 The second player
	 */
	public GameSession(PregamePlayerConfiguration player1, PregamePlayerConfiguration player2) {
		this.id = RandomStringUtils.randomAlphanumeric(30);
	}

	/**
	 * An identifier the matchmaking API can use to identify this particular game session for management tasks, like
	 * cleaning up a game (closing its sockets) or sending in-game messages.
	 *
	 * @return The ID
	 */
	public String getId() {
		return id;
	}
}
