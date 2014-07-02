package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class OverloadEvent extends GameEvent {

	private int playerId;

	public OverloadEvent(GameContext context, int playerId) {
		super(context);
		this.playerId = playerId;
	}

	@Override
	public Entity getEventTarget() {
		return null;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.OVERLOAD;
	}

	public int getPlayerId() {
		return playerId;
	}

}
