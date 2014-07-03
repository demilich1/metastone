package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.SilenceEvent;

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
