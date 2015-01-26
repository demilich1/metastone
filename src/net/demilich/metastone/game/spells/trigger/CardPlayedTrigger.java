package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.CardPlayedEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.TargetPlayer;

public class CardPlayedTrigger extends GameEventTrigger {
	
	private final TargetPlayer targetPlayer;
	private final CardType cardType;

	public CardPlayedTrigger(TargetPlayer targetPlayer) {
		this(targetPlayer, null);
	}
	
	public CardPlayedTrigger(TargetPlayer targetPlayer, CardType cardType) {
		this.targetPlayer = targetPlayer;
		this.cardType = cardType;
	}

	@Override
	public boolean fire(GameEvent event, Entity host) {
		CardPlayedEvent cardPlayedEvent = (CardPlayedEvent) event;
		switch (targetPlayer) {
		case BOTH:
			return true;
		case SELF:
			return cardPlayedEvent.getPlayerId() == host.getOwner();
		case OPPONENT:
			return cardPlayedEvent.getPlayerId() != host.getOwner();
		}
		if (cardType != null) {
			return cardPlayedEvent.getCard().getCardType() == cardType;
		}
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.PLAY_CARD;
	}

}
