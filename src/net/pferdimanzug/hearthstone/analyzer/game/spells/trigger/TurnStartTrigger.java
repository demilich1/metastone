package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.TurnStartEvent;

public class TurnStartTrigger extends GameEventTrigger {
	
	@Override
	public boolean fire(IGameEvent event, Entity host) {
		TurnStartEvent turnStartEvent = (TurnStartEvent) event;
		return turnStartEvent.getPlayer() == getOwner();
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.TURN_START;
	}

}
