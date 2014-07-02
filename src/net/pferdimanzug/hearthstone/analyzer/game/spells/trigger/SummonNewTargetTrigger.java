package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;

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
