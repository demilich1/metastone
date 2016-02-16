package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;

public class SilenceEvent extends GameEvent {

	private final Minion target;

	public SilenceEvent(GameContext context, Minion target) {
		super(context);
		this.target = target;
	}
	
	@Override
	public Entity getEventSource() {
		return null;
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
