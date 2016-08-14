package net.demilich.metastone.game.spells.trigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.PreDamageEvent;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class FatalDamageTrigger extends PreDamageTrigger {
	
	private static Logger logger = LoggerFactory.getLogger(FatalDamageTrigger.class);

	public FatalDamageTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		if (!super.fire(event, host)) {
			return false;
		} else {
			PreDamageEvent preDamageEvent = (PreDamageEvent) event;
			Entity victim = preDamageEvent.getVictim();
			switch (victim.getEntityType()) {
			case HERO:
				Hero hero = (Hero) victim;
				return hero.getEffectiveHp() <= event.getGameContext().getDamageStack().peek();
			case MINION:
				Minion minion = (Minion) victim;
				return minion.getHp() <= event.getGameContext().getDamageStack().peek();
			default:
				logger.warn("Invalid entity type in FatalDamageTrigger: {}", victim);
				break;
			}
			  
		}
		return false;
	}

}
