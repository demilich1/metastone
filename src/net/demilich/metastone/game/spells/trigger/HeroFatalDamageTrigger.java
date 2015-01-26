package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.PhysicalAttackEvent;

public class HeroFatalDamageTrigger extends  HeroAttackedTrigger{
	
	@Override
	public boolean fire(GameEvent event, Entity host) {
		if (!super.fire(event, host)) {
			return false;
		}
		PhysicalAttackEvent physicalAttackEvent = (PhysicalAttackEvent) event;
		Hero hero = (Hero) physicalAttackEvent.getDefender();
		int effectiveHp = hero.getHp() + hero.getArmor();
		return physicalAttackEvent.getDamageDealt() >= effectiveHp;
	}

}
