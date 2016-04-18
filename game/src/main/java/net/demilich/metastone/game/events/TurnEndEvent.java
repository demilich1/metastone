package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;

public class TurnEndEvent extends GameEvent {

	public TurnEndEvent(GameContext context, int playerId) {
		super(context, playerId, -1);
	}
	
	@Override
	public Entity getEventTarget() {
		return null;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.TURN_END;
	}

}
