package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.DiscardEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class DiscardTrigger extends GameEventTrigger {

	public DiscardTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		DiscardEvent discardEvent = (DiscardEvent) event;
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		EntityReference target = (EntityReference) desc.get(EventTriggerArg.TARGET);
		if (target == EntityReference.SELF && discardEvent.getCard() != host) {
			return false;
		}
		
		int targetPlayerId = discardEvent.getPlayerId();
		if (targetPlayer != null) {
			return determineTargetPlayer(discardEvent, targetPlayer, host, targetPlayerId);
		}
		
		return true;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.DISCARD;
	}

}
