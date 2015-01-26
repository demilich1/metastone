package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;

public class SummonNewTargetTrigger extends TargetAcquisitionTrigger {

	public SummonNewTargetTrigger(ActionType actionType) {
		super(actionType);
	}

	@Override
	public boolean fire(GameEvent event, Entity host) {
		if (!super.fire(event, host)) {
			return false;
		}

		// this is used by secrets like NobleSacrifice, which will summon a new
		// minion to the board, so it should only trigger if the cap has not yet
		// been reached
		Player owner = event.getGameContext().getPlayer(getOwner());
		if (!event.getGameContext().getLogic().canSummonMoreMinions(owner)) {
			return false;
		} 
		return true;
	}

}
