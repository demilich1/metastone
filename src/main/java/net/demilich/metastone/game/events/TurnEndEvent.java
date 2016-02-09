package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;

public class TurnEndEvent extends GameEvent {

	private final int playerId;

	public TurnEndEvent(GameContext context, int playerId) {
		super(context);
		this.playerId = playerId;
	}

	@Override
	public Entity getEventTarget() {
		return null;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.TURN_END;
	}

	public int getPlayerId() {
		return playerId;
	}

}
