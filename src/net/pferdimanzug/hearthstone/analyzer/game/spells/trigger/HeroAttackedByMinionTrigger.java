package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.PhysicalAttackEvent;

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
