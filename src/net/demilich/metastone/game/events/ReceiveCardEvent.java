package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;

public class ReceiveCardEvent extends GameEvent {

	private final Card card;
	private final int playerId;

	public ReceiveCardEvent(GameContext context, int playerId, Card card) {
		super(context);
		this.playerId = playerId;
		this.card = card;
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
		return GameEventType.RECEIVE_CARD;
	}

	public int getPlayerId() {
		return playerId;
	}

}
