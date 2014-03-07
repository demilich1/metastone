package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.PhysicalAttackEvent;

public class PhysicalAttackTrigger extends GameEventTrigger {

	@Override
	public boolean fire(IGameEvent event, Actor host) {
		PhysicalAttackEvent physicalAttackEvent = (PhysicalAttackEvent) event;
		return physicalAttackEvent.getAttacker() == host && physicalAttackEvent.getDamageDealt() > 0;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.PHYSICAL_ATTACK;
	}

}
