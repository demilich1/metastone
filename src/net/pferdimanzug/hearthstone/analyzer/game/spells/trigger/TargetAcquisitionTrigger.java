package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.TargetAcquisitionEvent;

public class TargetAcquisitionTrigger extends GameEventTrigger {

	@Override
	public boolean fire(GameEvent event, Actor host) {
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
