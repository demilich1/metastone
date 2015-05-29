package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;

public class FatalDamageEvent extends GameEvent {
	
	private final Entity victim;

	public FatalDamageEvent(GameContext context, Entity victim) {
		super(context);
		this.victim = victim;
	}

	@Override
	public Entity getEventTarget() {
		return victim;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.FATAL_DAMAGE;
	}

}
