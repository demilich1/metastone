package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.KillEvent;

public class MinionDiesTrigger extends SecretTrigger {

	@Override
	protected boolean secretTriggered(GameEvent event, Actor host) {
		KillEvent killEvent = (KillEvent) event;
		return killEvent.getVictim().getOwner() == getOwner();
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.KILL;
	}
	

}
