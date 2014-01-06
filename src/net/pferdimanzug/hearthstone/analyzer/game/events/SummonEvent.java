package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class SummonEvent extends GameEvent {
	
	private final Entity minion;

	public SummonEvent(GameContext context, Entity minion) {
		super(context);
		this.minion = minion;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.SUMMON;
	}

	public Entity getMinion() {
		return minion;
	}



}
