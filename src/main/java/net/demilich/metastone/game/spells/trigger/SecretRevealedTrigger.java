package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.SecretRevealedEvent;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class SecretRevealedTrigger extends GameEventTrigger {

	public SecretRevealedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		SecretRevealedEvent secretRevealedEvent = (SecretRevealedEvent) event;
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		switch (targetPlayer) {
		case BOTH:
			return true;
		case SELF:
		case OWNER:
			return secretRevealedEvent.getPlayerId() == host.getOwner();
		case OPPONENT:
			return secretRevealedEvent.getPlayerId() != host.getOwner();
		default:
			break;
		}
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.SECRET_REVEALED;
	}

}
