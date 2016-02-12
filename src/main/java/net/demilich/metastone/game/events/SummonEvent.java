package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;

public class SummonEvent extends GameEvent {

	private final Minion minion;
	private final Card source;

	public SummonEvent(GameContext context, Minion minion, Card source) {
		super(context);
		this.minion = minion;
		this.source = source;
	}
	
	@Override
	public Entity getEventSource() {
		return null;
	}

	@Override
	public Entity getEventTarget() {
		return getMinion();
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

}
