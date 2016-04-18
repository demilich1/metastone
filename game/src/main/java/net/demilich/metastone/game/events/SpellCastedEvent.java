package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;

public class SpellCastedEvent extends GameEvent {

	private final Card sourceCard;

	public SpellCastedEvent(GameContext context, int playerId, Card sourceCard) {
		super(context, playerId, playerId);
		this.sourceCard = sourceCard;
	}
	
	@Override
	public Entity getEventSource() {
		return getSourceCard();
	}

	@Override
	public Entity getEventTarget() {
		return getSourceCard();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.SPELL_CASTED;
	}

	public Card getSourceCard() {
		return sourceCard;
	}

}
