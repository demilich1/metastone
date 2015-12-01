package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.DamageEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class DamageReceivedOnTurnTrigger extends DamageReceivedTrigger {

	public DamageReceivedOnTurnTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		DamageEvent damageEvent = (DamageEvent) event;
		if (event.getGameContext().getActivePlayer().getId() != damageEvent.getEventTarget().getOwner()) {
			return false;
		}
		
		return super.fire(event, host);
	}

}
