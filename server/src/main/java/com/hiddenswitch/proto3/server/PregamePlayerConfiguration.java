package com.hiddenswitch.proto3.server;

import net.demilich.metastone.game.decks.Bench;

public class PregamePlayerConfiguration {
	private final Bench deck;
	private final String name;

	public PregamePlayerConfiguration(Bench deck, String name) {
		this.deck = deck;
		this.name = name;
	}

	public Bench getDeck() {
		return deck;
	}

	public String getName() {
		return name;
	}
}
