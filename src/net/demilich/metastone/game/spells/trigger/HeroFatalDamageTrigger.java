package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.PhysicalAttackEvent;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class HeroFatalDamageTrigger extends  HeroAttackedTrigger{
	
	public HeroFatalDamageTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		if (!super.fire(event, host)) {
			return false;
		}
		PhysicalAttackEvent physicalAttackEvent = (PhysicalAttackEvent) event;
		Hero hero = (Hero) physicalAttackEvent.getDefender();
		int effectiveHp = hero.getEffectiveHp();
		return physicalAttackEvent.getDamageDealt() >= effectiveHp;
	}

}
