package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.PhysicalAttackEvent;

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
