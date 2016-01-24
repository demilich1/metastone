package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.PreDamageEvent;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class FatalDamageTrigger extends PreDamageTrigger {

	public FatalDamageTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		if (!super.fire(event, host)) {
			return false;
		} else {
			PreDamageEvent preDamageEvent = (PreDamageEvent) event;
			return ((Actor) preDamageEvent.getVictim()).getHp() <= event.getGameContext().getDamageStack().peek(); 
		}
	}

}
