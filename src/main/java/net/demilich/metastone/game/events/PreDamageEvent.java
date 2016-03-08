package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;

public class PreDamageEvent extends GameEvent {

	private final Entity victim;
	private final Entity source;

	public PreDamageEvent(GameContext context, Entity victim, Entity source) {
		super(context, victim.getOwner(), source.getOwner());
		this.victim = victim;
		this.source = source;
	}
	
	@Override
	public Entity getEventTarget() {
		return getVictim();
	}
	
	@Override
	public Entity getEventSource() {
		return getSource();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.PRE_DAMAGE;
	}

	public Entity getSource() {
		return source;
	}

	public Entity getVictim() {
		return victim;
	}

}
