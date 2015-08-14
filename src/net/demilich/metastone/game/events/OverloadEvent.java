package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;

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

	public int getPlayer() {
		return playerId;
	}

}
