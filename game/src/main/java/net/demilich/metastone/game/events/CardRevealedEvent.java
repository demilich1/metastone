package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;

public class CardRevealedEvent extends GameEvent {

	private final Card card;
	private final double delay;

	public CardRevealedEvent(GameContext context, int playerId, Card card, double delay) {
		super(context, playerId, -1);
		this.card = card;
		this.delay = delay;
	}

	public Card getCard() {
		return card;
	}
	
	public double getDelay() {
		return delay;
	}
	
	@Override
	public Entity getEventTarget() {
		return getCard();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.REVEAL_CARD;
	}
}
