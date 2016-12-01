package com.hiddenswitch.proto3.server;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.decks.Deck;

public class PregamePlayerConfiguration {
	private final Deck deck;
	private final String name;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	private Player player;

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
