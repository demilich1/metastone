package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.PhysicalAttackEvent;

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
