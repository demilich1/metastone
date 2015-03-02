package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;

public class EnrageChangedTrigger extends GameEventTrigger {

	@Override
	public boolean fire(GameEvent event, Entity host) {
		return event.getEventTarget() == host;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.ENRAGE_CHANGED;
	}

}
