package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;

public class SummonEvent extends GameEvent {
	
	private final Actor minion;

	public SummonEvent(GameContext context, Actor minion) {
		super(context);
		this.minion = minion;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.SUMMON;
	}

	public Actor getMinion() {
		return minion;
	}



}
