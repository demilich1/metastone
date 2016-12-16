package com.hiddenswitch.proto3.net.common;

import net.demilich.metastone.game.decks.Bench;
import net.demilich.metastone.game.decks.Deck;

import java.io.Serializable;

public class MatchmakingRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	public Deck deck;
	public String userId;

	public MatchmakingRequest withUserId(String userId) {
		this.userId = userId;
		return this;
	}
}

