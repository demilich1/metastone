package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.TargetAcquisitionEvent;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class TargetAcquisitionTrigger extends GameEventTrigger {

	public TargetAcquisitionTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		TargetAcquisitionEvent targetAcquisitionEvent = (TargetAcquisitionEvent) event;

		ActionType actionType = (ActionType) desc.get(EventTriggerArg.ACTION_TYPE);
		if (targetAcquisitionEvent.getActionType() != actionType) {
			return false;
		}
		EntityType sourceEntityType = (EntityType) desc.get(EventTriggerArg.SOURCE_ENTITY_TYPE);
		if (sourceEntityType != null && sourceEntityType != targetAcquisitionEvent.getSource().getEntityType()) {
			return false;
		}
		EntityType targetEntityType = (EntityType) desc.get(EventTriggerArg.TARGET_ENTITY_TYPE);
		if (targetEntityType != null && targetEntityType != targetAcquisitionEvent.getTarget().getEntityType()) {
			return false;
		}
		
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		int targetPlayerId = targetAcquisitionEvent.getTarget().getOwner();
		if(targetPlayer != null && !determineTargetPlayer(targetAcquisitionEvent, targetPlayer, host, targetPlayerId)) {
			return false;
		}

		TargetPlayer sourcePlayer = desc.getSourcePlayer();
		int sourcePlayerId = targetAcquisitionEvent.getSource().getOwner();
		if (sourcePlayer != null) {
			return determineTargetPlayer(targetAcquisitionEvent, sourcePlayer, host, sourcePlayerId);
		}
		
		return true;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.TARGET_ACQUISITION;
	}
}
