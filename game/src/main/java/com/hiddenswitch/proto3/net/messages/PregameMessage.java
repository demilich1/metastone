package com.hiddenswitch.proto3.net.messages;

import net.demilich.metastone.game.decks.Deck;

public class PregameMessage extends NetworkMessage {
	private Deck deck;
	private String name;

	protected PregameMessage() {
		setMessageType(MessageType.PREGAME);
	}

	public PregameMessage(Deck deck, String name) {
		setDeck(deck);
		setName(name);
	}

	public Deck getDeck() {
		return deck;
	}

	private void setDeck(Deck deck) {
		this.deck = deck;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}
}
