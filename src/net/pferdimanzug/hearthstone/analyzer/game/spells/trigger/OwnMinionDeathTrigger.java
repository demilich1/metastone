package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.KillEvent;

public class OwnMinionDeathTrigger extends GameEventTrigger {

	@Override
	public boolean fire(GameEvent event, Entity host) {
		KillEvent killEvent = (KillEvent) event;
		return killEvent.getVictim().getOwner() == getOwner();
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.KILL;
	}

}
