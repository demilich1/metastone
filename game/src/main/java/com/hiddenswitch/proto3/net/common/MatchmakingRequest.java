package com.hiddenswitch.proto3.net.common;

import net.demilich.metastone.game.decks.Bench;
import net.demilich.metastone.game.decks.Deck;

import java.io.Serializable;

public class MatchmakingRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	public Deck deck;
	public String userId;
	public boolean allowBots;

	public MatchmakingRequest() {
	}

	public MatchmakingRequest(MatchmakingRequest other) {
		this.allowBots = other.isAllowBots();
		this.deck = other.getDeck();
		this.userId = other.getUserId();
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isAllowBots() {
		return allowBots;
	}

	public void setAllowBots(boolean allowBots) {
		this.allowBots = allowBots;
	}

	public MatchmakingRequest withDeck(final Deck deck) {
		this.deck = deck;
		return this;
	}

	public MatchmakingRequest withUserId(final String userId) {
		this.userId = userId;
		return this;
	}

	public MatchmakingRequest withAllowBots(final boolean allowBots) {
		this.allowBots = allowBots;
		return this;
	}
}

