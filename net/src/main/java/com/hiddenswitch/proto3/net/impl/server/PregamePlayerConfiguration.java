package com.hiddenswitch.proto3.net.impl.server;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.decks.Deck;

import java.io.Serializable;

public class PregamePlayerConfiguration implements Serializable {
	private final Deck deck;
	private final String name;
	private Player player;

	public PregamePlayerConfiguration(Deck deck, String name) {
		this.deck = deck;
		this.name = name;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public PregamePlayerConfiguration withPlayer(Player player1) {
		setPlayer(player1);
		return this;
	}

	public Deck getDeck() {
		return deck;
	}

	public String getName() {
		return name;
	}
}
