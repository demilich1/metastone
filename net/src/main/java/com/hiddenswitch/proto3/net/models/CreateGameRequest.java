package com.hiddenswitch.proto3.net.models;

import net.demilich.metastone.game.decks.Deck;

import java.io.Serializable;

public class CreateGameRequest implements Serializable {
	public long randomSeed;
	public Deck player1Deck;
	public Deck player2Deck;
	public String player1Name;
	public String player2Name;
}
