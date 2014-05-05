package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.TargetAcquisitionEvent;

public class SummonNewTargetTrigger extends SecretTrigger {

	@Override
	protected boolean secretTriggered(GameEvent event, Actor host) {
		// this is used by secrets like NobleSacrifice, which will summon a new minion
		// to the board, so it should only trigger if the cap has not yet been reached
		Player owner = event.getGameContext().getPlayer(getOwner());
		if (!event.getGameContext().getLogic().canSummonMoreMinions(owner)) {
			return false; 
		}
		TargetAcquisitionEvent targetAcquisitionEvent = (TargetAcquisitionEvent) event;
		if (targetAcquisitionEvent.getTargetAcquisition().getActionType() == ActionType.PHYSICAL_ATTACK) {
			targetAcquisitionEvent.getTargetAcquisition().setTarget(host);
			return true;
		}
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.TARGET_ACQUISITION;
	}

}
