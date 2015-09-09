package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;

public class JoustEvent extends GameEvent {

	private final int playerId;
	private final boolean won;
	private final Card ownCard;
	private final Card opponentCard;

	public JoustEvent(GameContext context, int playerId, boolean won, Card ownCard, Card opponentCard) {
		super(context);
		this.playerId = playerId;
		this.won = won;
		this.ownCard = ownCard;
		this.opponentCard = opponentCard;
	}

	@Override
	public Entity getEventTarget() {
		return ownCard;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.JOUST;
	}

	public int getPlayerId() {
		return playerId;
	}

	public boolean isWon() {
		return won;
	}

	public Card getOwnCard() {
		return ownCard;
	}

	public Card getOpponentCard() {
		return opponentCard;
	}

}
