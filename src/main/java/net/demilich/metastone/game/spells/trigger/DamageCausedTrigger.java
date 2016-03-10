package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.events.DamageEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class DamageCausedTrigger extends GameEventTrigger {

	public DamageCausedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		DamageEvent damageEvent = (DamageEvent) event;

		EntityType sourceEntityType = (EntityType) desc.get(EventTriggerArg.SOURCE_ENTITY_TYPE);
		if (sourceEntityType != null && sourceEntityType != damageEvent.getSource().getEntityType()) {
			return false;
		}
		EntityType targetEntityType = (EntityType) desc.get(EventTriggerArg.TARGET_ENTITY_TYPE);
		if (targetEntityType != null && targetEntityType != damageEvent.getVictim().getEntityType()) {
			return false;
		}

		return damageEvent.getSource() == host && damageEvent.getDamage() > 0;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.DAMAGE;
	}

}
