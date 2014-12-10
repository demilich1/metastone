package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class AfterSpellCastedEvent extends GameEvent {

	private final int playerId;
	private final Card sourceCard;

	public AfterSpellCastedEvent(GameContext context, int playerId, Card sourceCard) {
		super(context);
		this.playerId = playerId;
		this.sourceCard = sourceCard;
	}

	@Override
	public Entity getEventTarget() {
		return getSourceCard();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.AFTER_SPELL_CASTED;
	}

	public int getPlayerId() {
		return playerId;
	}

	public Card getSourceCard() {
		return sourceCard;
	}

}
