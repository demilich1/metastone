package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.entities.Entity;

public class DrawCardEvent extends GameEvent {

	private final Card card;
	private final CardType sourceType;

	public DrawCardEvent(GameContext context, int playerId, Card card, CardType sourceType) {
		super(context, playerId);
		this.card = card;
		this.sourceType = sourceType;
	}

	public Card getCard() {
		return card;
	}
	
	@Override
	public Entity getEventTarget() {
		return card;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.DRAW_CARD;
	}

	public CardType getSourceType() {
		return sourceType;
	}

}
