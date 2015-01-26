package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.DamageEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;

public class DamageReceivedTrigger extends GameEventTrigger {

	@Override
	public boolean fire(GameEvent event, Entity host) {
		DamageEvent damageEvent = (DamageEvent) event;
		return damageEvent.getVictim() == host;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.DAMAGE;
	}

}
