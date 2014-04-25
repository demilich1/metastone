package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.PhysicalAttackEvent;

public class MinionAttacksTrigger extends SecretTrigger {

	@Override
	protected boolean secretTriggered(GameEvent event, Actor host) {
		PhysicalAttackEvent physicalAttackEvent = (PhysicalAttackEvent) event;
		// checking for minion is sufficient, because secrets cannot activate during own turn
		// which means all PhysicalAttackEvents are always from hostile minions
		return physicalAttackEvent.getAttacker().getEntityType() == EntityType.MINION;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.PHYSICAL_ATTACK;
	}
	
	

}
