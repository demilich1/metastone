package com.hiddenswitch.proto3.net.models;

import com.hiddenswitch.proto3.server.PregamePlayerConfiguration;

public class CreateGameSessionRequest {
	private PregamePlayerConfiguration pregame1;
	private PregamePlayerConfiguration pregame2;

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
}
