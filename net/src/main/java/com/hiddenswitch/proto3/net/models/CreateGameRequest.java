package com.hiddenswitch.proto3.net.models;

import net.demilich.metastone.game.decks.Deck;

public class CreateGameRequest {
	public long randomSeed;
	public Deck player1Deck;
	public Deck player2Deck;
	public String player1Name;
	public String player2Name;
}
