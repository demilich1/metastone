package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;

public class SilenceEvent extends GameEvent {

	private final Minion target;

	public SilenceEvent(GameContext context, int playerId, Minion target) {
		super(context, playerId, -1);
		this.target = target;
	}
	
	@Override
	public Entity getEventTarget() {
		return getTarget();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.SILENCE;
	}

	public Minion getTarget() {
		return target;
	}

}
