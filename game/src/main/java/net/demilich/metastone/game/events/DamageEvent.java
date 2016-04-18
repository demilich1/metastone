package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;

public class DamageEvent extends GameEvent {

	private final Entity victim;
	private final int damage;
	private final Entity source;

	public DamageEvent(GameContext context, Entity victim, Entity source, int damage) {
		super(context, victim.getOwner(), source.getOwner());
		this.victim = victim;
		this.source = source;
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}
	
	@Override
	public Entity getEventSource() {
		return getSource();
	}

	@Override
	public Entity getEventTarget() {
		return getVictim();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.DAMAGE;
	}

	public Entity getSource() {
		return source;
	}

	public Entity getVictim() {
		return victim;
	}

}
