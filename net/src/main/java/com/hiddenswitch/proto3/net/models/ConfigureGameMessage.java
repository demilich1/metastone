package com.hiddenswitch.proto3.net.models;

import net.demilich.metastone.game.decks.Deck;

public class ConfigureGameMessage extends NetworkMessage {
	private long randomSeed;
	private Deck player1Deck;
	private Deck player2Deck;
	private String player1Name;
	private String player2Name;

	protected ConfigureGameMessage() {
		setMessageType(MessageType.CONFIGURE_GAME);
	}

	public ConfigureGameMessage(long seed, Deck player1Deck, Deck player2Deck, String player1Name, String player2Name) {
		setRandomSeed(seed);
		setPlayer1Deck(player1Deck);
		setPlayer2Deck(player2Deck);
		setPlayer1Name(player1Name);
		setPlayer2Name(player1Name);
	}

	public long getRandomSeed() {
		return randomSeed;
	}

	protected void setRandomSeed(long randomSeed) {
		this.randomSeed = randomSeed;
	}

	public Deck getPlayer1Deck() {
		return player1Deck;
	}

	private void setPlayer1Deck(Deck player1Deck) {
		this.player1Deck = player1Deck;
	}

	public Deck getPlayer2Deck() {
		return player2Deck;
	}

	private void setPlayer2Deck(Deck player2Deck) {
		this.player2Deck = player2Deck;
	}

	public String getPlayer1Name() {
		return player1Name;
	}

	private void setPlayer1Name(String player1Name) {
		this.player1Name = player1Name;
	}

	public String getPlayer2Name() {
		return player2Name;
	}

	private void setPlayer2Name(String player2Name) {
		this.player2Name = player2Name;
	}
}
