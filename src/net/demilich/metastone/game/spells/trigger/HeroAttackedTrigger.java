package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.PhysicalAttackEvent;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class HeroAttackedTrigger extends GameEventTrigger {

	public HeroAttackedTrigger(EventTriggerDesc desc) {
		super(desc);
	}
	
	@Override
	protected boolean fire(GameEvent event, Entity host) {
		PhysicalAttackEvent physicalAttackEvent = (PhysicalAttackEvent) event;
		return physicalAttackEvent.getDefender() == host;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.PHYSICAL_ATTACK;
	}

}
