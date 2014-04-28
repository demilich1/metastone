package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;

public class MinionSummonedTrigger extends SecretTrigger {

	@Override
	protected boolean secretTriggered(GameEvent event, Actor host) {
		return true;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.SUMMON;
	}

}
