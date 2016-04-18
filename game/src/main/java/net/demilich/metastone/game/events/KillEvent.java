package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;

public class KillEvent extends GameEvent {

	private final Entity victim;

	public KillEvent(GameContext context, Entity victim) {
		super(context, victim.getOwner(), -1);
		this.victim = victim;
	}
	
	@Override
	public Entity getEventTarget() {
		return getVictim();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.KILL;
	}

	public Entity getVictim() {
		return victim;
	}

}
