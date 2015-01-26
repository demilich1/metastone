package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.ReceiveCardEvent;
import net.demilich.metastone.game.spells.TargetPlayer;

public class CardReceivedTrigger extends GameEventTrigger {

	private final TargetPlayer targetPlayer;

	public CardReceivedTrigger(TargetPlayer targetPlayer) {
		this.targetPlayer = targetPlayer;
	}

	@Override
	public boolean fire(GameEvent event, Entity host) {
		ReceiveCardEvent receiveCardEvent = (ReceiveCardEvent) event;
		switch (targetPlayer) {
		case BOTH:
			return true;
		case OPPONENT:
			return host.getOwner() != receiveCardEvent.getPlayerId();
		case SELF:
			return host.getOwner() == receiveCardEvent.getPlayerId();
		default:
			break;
		
		}
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.RECEIVE_CARD;
	}

}
