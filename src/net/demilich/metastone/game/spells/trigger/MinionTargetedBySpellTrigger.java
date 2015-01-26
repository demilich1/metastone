package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.TargetAcquisitionEvent;

public class MinionTargetedBySpellTrigger extends SummonNewTargetTrigger {

	public MinionTargetedBySpellTrigger() {
		super(ActionType.SPELL);
	}
	
	@Override
	public boolean fire(GameEvent event, Entity host) {
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
