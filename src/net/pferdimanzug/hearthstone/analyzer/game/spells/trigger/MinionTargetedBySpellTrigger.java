package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.TargetAcquisitionEvent;

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
