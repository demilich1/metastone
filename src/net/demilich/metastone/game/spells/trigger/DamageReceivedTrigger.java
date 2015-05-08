package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.events.DamageEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class DamageReceivedTrigger extends GameEventTrigger {

	public DamageReceivedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		DamageEvent damageEvent = (DamageEvent) event;
		
		EntityType entityType = (EntityType) desc.get(EventTriggerArg.ENTITY_TYPE);
		if (entityType != null && damageEvent.getVictim().getEntityType() != entityType) {
			return false;
		}
		
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		switch (targetPlayer) {
		case BOTH:
			return true;
		case OPPONENT:
			return damageEvent.getVictim().getOwner() != getOwner();
		case SELF:
			return damageEvent.getVictim().getOwner() == getOwner();
		}
		
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.DAMAGE;
	}

}
