package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;

public class HealEvent extends GameEvent {

	private final Actor target;
	private final int healing;

	public HealEvent(GameContext context, Actor target, int healing) {
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

	public Actor getTarget() {
		return target;
	}

}
