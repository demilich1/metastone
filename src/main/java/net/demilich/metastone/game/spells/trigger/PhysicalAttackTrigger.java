package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.PhysicalAttackEvent;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class PhysicalAttackTrigger extends GameEventTrigger {

	public PhysicalAttackTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		EntityType sourceEntityType = (EntityType) desc.get(EventTriggerArg.ENTITY_TYPE);
		EntityType targetEntityType = (EntityType) desc.get(EventTriggerArg.TARGET_ENTITY_TYPE);
		PhysicalAttackEvent physicalAttackEvent = (PhysicalAttackEvent) event;
		if (sourceEntityType != null && physicalAttackEvent.getAttacker().getEntityType() != sourceEntityType) {
			return false;
		}
		if (targetEntityType != null && physicalAttackEvent.getDefender().getEntityType() != targetEntityType) {
			return false;
		}
		
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		switch (targetPlayer) {
		case BOTH:
			return true;
		case OPPONENT:
			return physicalAttackEvent.getAttacker().getOwner() != getOwner();
		case SELF:
		case OWNER:
			return physicalAttackEvent.getAttacker().getOwner() == getOwner();
		case ACTIVE:
			return physicalAttackEvent.getAttacker().getOwner() == event.getGameContext().getActivePlayerId();
		}
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.PHYSICAL_ATTACK;
	}
}
