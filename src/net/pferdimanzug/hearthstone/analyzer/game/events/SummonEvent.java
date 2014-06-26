package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class SummonEvent extends GameEvent {
	
	private final Minion minion;
	private final Card source;

	public SummonEvent(GameContext context, Minion minion, Card source) {
		super(context);
		this.minion = minion;
		this.source = source;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.SUMMON;
	}

	public Minion getMinion() {
		return minion;
	}

	public Card getSource() {
		return source;
	}

	@Override
	public Entity getEventTarget() {
		return getMinion();
	}



}
