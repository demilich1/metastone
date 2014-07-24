package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.CardPlayedEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;

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
		return GameEventType.CARD_PLAYED;
	}

}
