package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.events.FatalDamageEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class HeroFatalDamageTrigger extends GameEventTrigger {

	public HeroFatalDamageTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		FatalDamageEvent damageEvent = (FatalDamageEvent) event;
		if (damageEvent.getEventTarget().getEntityType() != EntityType.HERO) {
			return false;
		}
		return true;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.FATAL_DAMAGE;
	}

}
