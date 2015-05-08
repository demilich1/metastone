package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.events.DamageEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class HeroDamagedTrigger extends GameEventTrigger {

	public HeroDamagedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		DamageEvent damageEvent = (DamageEvent) event;
		Actor victim = (Actor) damageEvent.getVictim();
		return victim.getEntityType() == EntityType.HERO && victim.getOwner() == getOwner();
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.DAMAGE;
	}

}
