package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.TargetAcquisitionEvent;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class FriendlyTargetedTrigger extends TargetAcquisitionTrigger {

	public FriendlyTargetedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		TargetAcquisitionEvent targetAcquisitionEvent = (TargetAcquisitionEvent) event;
		if (host.getOwner() != targetAcquisitionEvent.getEventTarget().getOwner()) {
			return false;
		}
		
		return super.fire(event, host);
	}

}
