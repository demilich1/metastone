package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;

public class OverloadEvent extends GameEvent {

	private Card card;

	public OverloadEvent(GameContext context, int playerId, Card card) {
		super(context, playerId, -1);
		this.card = card;
	}
	
	public Card getCard() {
		return card;
	}
	
	@Override
	public Entity getEventTarget() {
		return getCard();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.OVERLOAD;
	}

}
