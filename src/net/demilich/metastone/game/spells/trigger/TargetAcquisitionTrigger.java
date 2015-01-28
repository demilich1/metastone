package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.TargetAcquisitionEvent;

public class TargetAcquisitionTrigger extends GameEventTrigger {

	private final ActionType actionType;
	private final EntityType sourceEntityType;

	public TargetAcquisitionTrigger(ActionType actionType) {
		this(actionType, null);
	}

	public TargetAcquisitionTrigger(ActionType actionType, EntityType sourceEntityType) {
		this.actionType = actionType;
		this.sourceEntityType = sourceEntityType;
	}

	@Override
	public boolean fire(GameEvent event, Entity host) {
		TargetAcquisitionEvent targetAcquisitionEvent = (TargetAcquisitionEvent) event;
		if (targetAcquisitionEvent.getActionType() != actionType) {
			return false;
		}
		if (sourceEntityType != null && sourceEntityType != targetAcquisitionEvent.getSource().getEntityType()) {
			return false;
		}
		return true;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.TARGET_ACQUISITION;
	}
}
