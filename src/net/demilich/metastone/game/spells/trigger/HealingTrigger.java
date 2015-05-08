package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.HealEvent;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class HealingTrigger extends GameEventTrigger {

	public HealingTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		EntityType entityType = (EntityType) desc.get(EventTriggerArg.ENTITY_TYPE);
		
		if (entityType != null) {
			HealEvent healEvent = (HealEvent) event;
			if (healEvent.getTarget().getEntityType() != entityType) {
				return false;
			}
		}
		return true;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.HEAL;
	}

}
