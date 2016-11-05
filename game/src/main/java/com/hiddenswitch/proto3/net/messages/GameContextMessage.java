package com.hiddenswitch.proto3.net.messages;

import net.demilich.metastone.game.GameContext;

public class GameContextMessage extends NetworkMessage {
	private GameContext gameContext;

	protected GameContextMessage() {
		super();
		setMessageType(MessageType.GAME_CONTEXT);
	}

	public GameContextMessage(int order, GameContext gameContext) {
		super(order);
		setGameContext(gameContext);
		setMessageType(MessageType.GAME_CONTEXT);
	}

	public GameContext getGameContext() {
		return gameContext;
	}

	private void setGameContext(GameContext gameContext) {
		this.gameContext = gameContext;
	}
}
