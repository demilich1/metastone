package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class HealEvent extends GameEvent {

	private final Entity target;
	private final int healing;

	public HealEvent(GameContext context, Entity target, int healing) {
		super(context);
		this.target = target;
		this.healing = healing;
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

	@Override
	public Entity getEventTarget() {
		return getTarget();
	}

}
