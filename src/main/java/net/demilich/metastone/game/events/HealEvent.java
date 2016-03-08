package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;

public class HealEvent extends GameEvent {

	private final Entity target;
	private final int healing;

	public HealEvent(GameContext context, int playerId, Entity target, int healing) {
		super(context, playerId, -1);
		this.target = target;
		this.healing = healing;
	}

	@Override
	public Entity getEventTarget() {
		return getTarget();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.HEAL;
	}

	public int getHealing() {
		return healing;
	}

	public Entity getTarget() {
		return target;
	}

}
