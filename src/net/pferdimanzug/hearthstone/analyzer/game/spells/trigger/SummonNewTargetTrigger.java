package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.TargetAcquisitionEvent;

public class SummonNewTargetTrigger extends GameEventTrigger {
	
	private final ActionType actionType;

	public SummonNewTargetTrigger(ActionType actionType) {
		this.actionType = actionType;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.TARGET_ACQUISITION;
	}

	@Override
	public boolean fire(GameEvent event, Actor host) {
		// this is used by secrets like NobleSacrifice, which will summon a new
		// minion to the board, so it should only trigger if the cap has not yet
		// been reached
		Player owner = event.getGameContext().getPlayer(getOwner());
		if (!event.getGameContext().getLogic().canSummonMoreMinions(owner)) {
			return false;
		}
		TargetAcquisitionEvent targetAcquisitionEvent = (TargetAcquisitionEvent) event;
		if (targetAcquisitionEvent.getActionType() == actionType) {
			return true;
		}
		return false;
	}

}
