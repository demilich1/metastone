package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.PhysicalAttackEvent;

public class HeroAttackedByMinionTrigger extends HeroAttackedTrigger {

	@Override
	public boolean fire(GameEvent event, Entity host) {
		if(!super.fire(event, host)) {
			return false;
		}
		PhysicalAttackEvent physicalAttackEvent = (PhysicalAttackEvent) event;
		return physicalAttackEvent.getAttacker().getEntityType() == EntityType.MINION;
	}

}
