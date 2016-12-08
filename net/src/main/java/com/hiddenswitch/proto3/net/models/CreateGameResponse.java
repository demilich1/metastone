package com.hiddenswitch.proto3.net.models;

import net.demilich.metastone.game.GameContext;

import java.io.Serializable;

public class CreateGameResponse implements Serializable {
	public GameContext startingGameContext;
	public String player1Name;
	public String player2Name;
}
