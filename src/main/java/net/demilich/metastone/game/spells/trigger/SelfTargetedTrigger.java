package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.TargetAcquisitionEvent;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class SelfTargetedTrigger extends TargetAcquisitionTrigger {

	public SelfTargetedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		TargetAcquisitionEvent targetAcquisitionEvent = (TargetAcquisitionEvent) event;
		if (targetAcquisitionEvent.getEventTarget() != host) {
			return false;
		}
		
		System.out.println("Targeted: " + targetAcquisitionEvent.getEventTarget());

		return super.fire(event, host);
	}

}
