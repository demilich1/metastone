package com.hiddenswitch.proto3.net.common;

import io.vertx.core.Handler;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;

import java.util.Arrays;
import java.util.List;

/**
 * Created by bberman on 12/5/16.
 */
public class GameplayRequest {
	public final Handler handler;
	public List<Card> starterCards;
	public GameplayRequestType type;
	public GameState state;
	public List<GameAction> actions;

	public GameplayRequest(GameplayRequestType type, GameState state, List<GameAction> actions, Handler handler) {
		this.type = type;
		this.handler = handler;
		this.state = state;
		this.actions = actions;
	}

	public GameplayRequest(GameplayRequestType type, List<Card> starterCards, Handler<List<Card>> callback) {
		this.type = type;
		this.starterCards = starterCards;
		this.handler = callback;
	}
}
