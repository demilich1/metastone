package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class DamageEvent extends GameEvent {
	
	private final Entity victim;
	private final int damage;

	public DamageEvent(GameContext context, Entity victim, int damage) {
		super(context);
		this.victim = victim;
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.DAMAGE;
	}

	public Entity getVictim() {
		return victim;
	}

	@Override
	public Entity getEventTarget() {
		return getVictim();
	}


}
