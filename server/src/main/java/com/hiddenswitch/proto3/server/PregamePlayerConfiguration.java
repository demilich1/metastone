package com.hiddenswitch.proto3.server;

import net.demilich.metastone.game.decks.Deck;

public class PregamePlayerConfiguration {
	private final Deck deck;
	private final String name;

	public PregamePlayerConfiguration(Deck deck, String name) {
		this.deck = deck;
		this.name = name;
	}

	public Deck getDeck() {
		return deck;
	}

	public String getName() {
		return name;
	}
}
