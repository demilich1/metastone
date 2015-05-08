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
		if (damageEvent.getSource() == null) {
			return false;
		}
		boolean worksOnHeroes = desc.getBool(EventTriggerArg.WORKS_ON_HERO);
		if (!worksOnHeroes && damageEvent.getVictim().getEntityType() == EntityType.HERO) {
			return false;
		}
		return damageEvent.getSource() == host;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.DAMAGE;
	}

}
