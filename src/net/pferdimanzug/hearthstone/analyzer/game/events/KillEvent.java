package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;

public class KillEvent extends GameEvent {

	private final Actor victim;

	public KillEvent(GameContext context, Actor victim) {
		super(context);
		this.victim = victim;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.KILL;
	}

	public Actor getVictim() {
		return victim;
	}

	

}
