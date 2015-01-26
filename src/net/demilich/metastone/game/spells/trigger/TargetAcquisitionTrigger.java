package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.TargetAcquisitionEvent;

public class TargetAcquisitionTrigger extends GameEventTrigger {

	private final ActionType actionType;

	public TargetAcquisitionTrigger(ActionType actionType) {
		this.actionType = actionType;
	}

	@Override
	public boolean fire(GameEvent event, Entity host) {
		TargetAcquisitionEvent targetAcquisitionEvent = (TargetAcquisitionEvent) event;
		return targetAcquisitionEvent.getActionType() == actionType;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.TARGET_ACQUISITION;
	}
}
