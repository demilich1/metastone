package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.CardPlayedEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;

public class CardPlayedTrigger extends GameEventTrigger {
	
	private final TargetPlayer targetPlayer;

	public CardPlayedTrigger(TargetPlayer targetPlayer) {
		this.targetPlayer = targetPlayer;
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
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.CARD_PLAYED;
	}

}
