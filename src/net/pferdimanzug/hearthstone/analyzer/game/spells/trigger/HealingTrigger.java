package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;

public class HealingTrigger extends GameEventTrigger {

	@Override
	public boolean fire(GameEvent event, Actor host) {
		return true;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.HEAL;
	}

}
