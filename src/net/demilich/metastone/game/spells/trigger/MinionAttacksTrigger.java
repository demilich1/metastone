package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.PhysicalAttackEvent;

public class MinionAttacksTrigger extends GameEventTrigger {

	@Override
	public boolean fire(GameEvent event, Entity host) {
		PhysicalAttackEvent physicalAttackEvent = (PhysicalAttackEvent) event;
		return physicalAttackEvent.getAttacker().getEntityType() == EntityType.MINION;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.PHYSICAL_ATTACK;
	}

}
