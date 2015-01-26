package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.SilenceEvent;

public class SilenceTrigger extends GameEventTrigger {

	@Override
	public boolean fire(GameEvent event, Entity host) {
		SilenceEvent silenceEvent = (SilenceEvent) event;
		return silenceEvent.getTarget() == host;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.SILENCE;
	}

}
