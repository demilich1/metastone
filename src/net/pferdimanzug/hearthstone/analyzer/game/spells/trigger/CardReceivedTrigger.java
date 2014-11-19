package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.ReceiveCardEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;

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
