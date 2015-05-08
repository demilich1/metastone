package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.TargetAcquisitionEvent;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class TargetedBySpellTrigger extends TargetAcquisitionTrigger {

	public TargetedBySpellTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		if (!super.fire(event, host)) {
			return false;
		}
		TargetAcquisitionEvent targetAcquisitionEvent = (TargetAcquisitionEvent) event;
		return targetAcquisitionEvent.getEventTarget() == host;
	}

}
