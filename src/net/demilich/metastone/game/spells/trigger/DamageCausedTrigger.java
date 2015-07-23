package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.PhysicalAttackEvent;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class DamageCausedTrigger extends GameEventTrigger {

	public DamageCausedTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		PhysicalAttackEvent physicalAttackEvent = (PhysicalAttackEvent) event;
		
		boolean worksOnHeroes = desc.getBool(EventTriggerArg.WORKS_ON_HERO);
		if (!worksOnHeroes && physicalAttackEvent.getDefender().getEntityType() == EntityType.HERO) {
			return false;
		}
		return physicalAttackEvent.getAttacker() == host && physicalAttackEvent.getDamageDealt() > 0;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.PHYSICAL_ATTACK;
	}

}
