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
		int targetPlayerId = secretRevealedEvent.getPlayerId();
		if (targetPlayer != null) {
			return determineTargetPlayer(secretRevealedEvent, targetPlayer, host, targetPlayerId);
		}
		
		return true;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.SECRET_REVEALED;
	}

}
