package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.KillEvent;

public class OwnMinionDeathTrigger extends GameEventTrigger {

	@Override
	public GameEventType interestedIn() {
		return GameEventType.KILL;
	}

	@Override
	public boolean fire(GameEvent event, Actor host) {
		KillEvent killEvent = (KillEvent) event;
		return killEvent.getVictim().getOwner() == getOwner();
	}

}
