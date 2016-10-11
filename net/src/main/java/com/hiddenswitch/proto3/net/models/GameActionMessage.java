package com.hiddenswitch.proto3.net.models;

import net.demilich.metastone.game.actions.GameAction;

public class GameActionMessage {
	private GameAction gameAction;

	public GameActionMessage(GameAction fromAction) {
		gameAction = fromAction.clone();
	}

	public GameAction getAction() {
		return gameAction;
	}
}
