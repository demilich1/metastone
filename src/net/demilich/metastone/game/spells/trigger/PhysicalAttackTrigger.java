package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.PhysicalAttackEvent;

public class PhysicalAttackTrigger extends GameEventTrigger {

	private final boolean worksOnHero;

	public PhysicalAttackTrigger(boolean worksOnHero) {
		this.worksOnHero = worksOnHero;
	}

	@Override
	public boolean fire(GameEvent event, Entity host) {
		PhysicalAttackEvent physicalAttackEvent = (PhysicalAttackEvent) event;
		if (physicalAttackEvent.getAttacker() != host) {
			return false;
		}
		if (!worksOnHero && physicalAttackEvent.getDefender().getEntityType() == EntityType.HERO) {
			return false;
		}
		return physicalAttackEvent.getDamageDealt() > 0;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.PHYSICAL_ATTACK;
	}

}
