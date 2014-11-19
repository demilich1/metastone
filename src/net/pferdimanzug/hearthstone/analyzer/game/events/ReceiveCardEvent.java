package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class ReceiveCardEvent extends GameEvent {

	private final Card card;
	private final int playerId;

	public ReceiveCardEvent(GameContext context, int playerId, Card card) {
		super(context);
		this.playerId = playerId;
		this.card = card;
	}

	@Override
	public Entity getEventTarget() {
		return card;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.RECEIVE_CARD;
	}

	public Card getCard() {
		return card;
	}

	public int getPlayerId() {
		return playerId;
	}

}
