package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class KillEvent extends GameEvent {

	private final Entity victim;

	public KillEvent(GameContext context, Entity victim) {
		super(context);
		this.victim = victim;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.KILL;
	}

	public Entity getVictim() {
		return victim;
	}

	@Override
	public Entity getEventTarget() {
		return getVictim();
	}

	

}
