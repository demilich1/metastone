package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.TargetAcquisitionEvent;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class SpellbenderTrigger extends SummonNewTargetTrigger {

	public SpellbenderTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		if (!super.fire(event, host)) {
			return false;
		}
		Player player = event.getGameContext().getPlayer(getOwner());
		if (!event.getGameContext().getLogic().canSummonMoreMinions(player)) {
			return false;
		}
		TargetAcquisitionEvent targetAcquisitionEvent = (TargetAcquisitionEvent) event;
		return targetAcquisitionEvent.getTarget().getEntityType() == EntityType.MINION;
	}
	
	

}
