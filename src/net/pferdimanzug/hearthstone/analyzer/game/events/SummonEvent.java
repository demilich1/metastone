package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class SummonEvent extends GameEvent {
	
	private final Minion minion;

	public SummonEvent(GameContext context, Minion minion) {
		super(context);
		this.minion = minion;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.SUMMON;
	}

	public Minion getMinion() {
		return minion;
	}



}
