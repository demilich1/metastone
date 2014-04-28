package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.PhysicalAttackEvent;

public class MinionAttackedTrigger extends SecretTrigger {

	@Override
	protected boolean secretTriggered(GameEvent event, Actor host) {
		PhysicalAttackEvent physicalAttackEvent = (PhysicalAttackEvent) event;
		return physicalAttackEvent.getDefender().getEntityType() == EntityType.MINION;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.PHYSICAL_ATTACK;
	}

}
