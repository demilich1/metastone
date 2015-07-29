package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.TargetAcquisitionEvent;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class HostAcquiresTargetTrigger extends TargetAcquisitionTrigger {

	public HostAcquiresTargetTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		TargetAcquisitionEvent targetAcquisitionEvent = (TargetAcquisitionEvent) event;
		if (targetAcquisitionEvent.getSource() != host) {
			return false;
		}
		return super.fire(event, host);
	}

}
