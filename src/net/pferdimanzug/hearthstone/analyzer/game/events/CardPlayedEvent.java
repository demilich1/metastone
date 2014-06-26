package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class CardPlayedEvent extends GameEvent {

	private final int playerId;
	private final Card card;

	public CardPlayedEvent(GameContext context, int playerId, Card card) {
		super(context);
		this.playerId = playerId;
		this.card = card;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.CARD_PLAYED;
	}

	public Card getCard() {
		return card;
	}

	public int getPlayerId() {
		return playerId;
	}

	@Override
	public Entity getEventTarget() {
		return getCard();
	}

}
