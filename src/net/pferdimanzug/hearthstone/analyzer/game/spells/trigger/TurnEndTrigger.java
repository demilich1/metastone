package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.TurnEndEvent;

public class TurnEndTrigger extends GameEventTrigger {
	
	@Override
	public boolean fire(IGameEvent event, Entity host) {
		TurnEndEvent turnEndEvent = (TurnEndEvent) event;
		return turnEndEvent.getPlayer() == getOwner();
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.TURN_END;
	}

}
