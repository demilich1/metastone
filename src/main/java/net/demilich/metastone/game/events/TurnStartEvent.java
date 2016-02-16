package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;

public class TurnStartEvent extends GameEvent {

	private final int playerId;

	public TurnStartEvent(GameContext context, int playerId) {
		super(context);
		this.playerId = playerId;
	}
	
	@Override
	public Entity getEventSource() {
		return null;
	}

	@Override
	public Entity getEventTarget() {
		return null;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.TURN_START;
	}

	public int getPlayerId() {
		return playerId;
	}

}
