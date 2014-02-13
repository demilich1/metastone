package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;

public class OverloadEvent extends GameEvent {

	private int playerId;

	public OverloadEvent(GameContext context, int playerId) {
		super(context);
		this.playerId = playerId;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.OVERLOAD;
	}

	public int getPlayerId() {
		return playerId;
	}

}
