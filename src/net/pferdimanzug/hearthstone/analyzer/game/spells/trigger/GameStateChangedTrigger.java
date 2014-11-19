package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;

public class GameStateChangedTrigger extends GameEventTrigger {

	@Override
	public boolean fire(GameEvent event, Entity host) {
		return true;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.ALL;
	}

}
