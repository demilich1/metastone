package com.hiddenswitch.proto3.net.messages;

import net.demilich.metastone.game.actions.GameAction;

public class GameActionMessage extends NetworkMessage {
	private GameAction gameAction;

	private GameActionMessage() {
		setMessageType(MessageType.GAME_ACTION);
	}

	public GameActionMessage(GameAction fromAction, int order) {
		this();
		setGameAction(fromAction);
		setOrder(order);
	}

	public GameAction getGameAction() {
		return gameAction;
	}

	private void setGameAction(GameAction gameAction) {
		this.gameAction = gameAction;
	}
}
