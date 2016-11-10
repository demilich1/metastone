package com.hiddenswitch.proto3.net.models;

import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.server.GameSession;
import com.hiddenswitch.proto3.server.PregamePlayerConfiguration;

public class CreateGameSessionResponse extends GameSession {
	private ClientConnectionConfiguration player1;
	private ClientConnectionConfiguration player2;

	/**
	 * Creates a new game session for the given two players. Once constructed, the session should
	 * be connectible from the clients given the ClientConnectionConfiguration provided by the API.
	 *
	 * @param player1 The first player
	 * @param player2 The second player
	 */
	public CreateGameSessionResponse(PregamePlayerConfiguration player1, PregamePlayerConfiguration player2) {
		super(player1, player2);
	}

	public CreateGameSessionResponse(ClientConnectionConfiguration player1, ClientConnectionConfiguration player2) {
		setPlayer1(player1);
		setPlayer2(player2);
	}

	public CreateGameSessionResponse() {
		super();
	}

	@Override
	public ClientConnectionConfiguration getConfigurationForPlayer1() {
		return player1;
	}

	@Override
	public ClientConnectionConfiguration getConfigurationForPlayer2() {
		return player2;
	}

	public void setPlayer1(ClientConnectionConfiguration player1) {
		this.player1 = player1;
	}

	public void setPlayer2(ClientConnectionConfiguration player2) {
		this.player2 = player2;
	}
}
