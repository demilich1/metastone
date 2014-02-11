package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.KillEvent;

public class MinionDeathTrigger extends GameEventTrigger {
	
	@Override
	public boolean fire(IGameEvent event, Actor host) {
		KillEvent killEvent = (KillEvent) event;
		return killEvent.getVictim().getEntityType() == EntityType.MINION;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.KILL;
	}

}
