package com.hiddenswitch.proto3.net.common;

import net.demilich.metastone.game.decks.Bench;
import net.demilich.metastone.game.decks.Deck;

public class MatchmakingRequest {
	public Deck deck;
	public String userId;

	public MatchmakingRequest withUserId(String userId) {
		this.userId = userId;
		return this;
	}
}

