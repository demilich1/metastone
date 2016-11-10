package com.hiddenswitch.proto3.net.models;

import com.hiddenswitch.proto3.server.PregamePlayerConfiguration;

public class CreateGameSessionRequest {
	private PregamePlayerConfiguration player1;
	private PregamePlayerConfiguration player2;

	public PregamePlayerConfiguration getPlayer1() {
		return player1;
	}

	public void setPlayer1(PregamePlayerConfiguration player1) {
		this.player1 = player1;
	}

	public PregamePlayerConfiguration getPlayer2() {
		return player2;
	}

	public void setPlayer2(PregamePlayerConfiguration player2) {
		this.player2 = player2;
	}
}
