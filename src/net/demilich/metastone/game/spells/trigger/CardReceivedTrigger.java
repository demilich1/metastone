package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.ReceiveCardEvent;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class CardReceivedTrigger extends GameEventTrigger {

	public CardReceivedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		ReceiveCardEvent receiveCardEvent = (ReceiveCardEvent) event;
		TargetPlayer targetPlayer = desc.getTargetPlayer();
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
