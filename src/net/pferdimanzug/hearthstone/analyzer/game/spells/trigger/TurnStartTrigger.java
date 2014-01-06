package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.TurnStartEvent;

public class TurnStartTrigger implements IGameEventTrigger {

	@Override
	public boolean fire(IGameEvent event, Entity host) {
		TurnStartEvent turnStartEvent = (TurnStartEvent) event;
		return turnStartEvent.getPlayer() == host.getOwner();
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.TURN_START;
	}

	@Override
	public Entity getTarget(GameContext context, Entity host) {
		return null;
	}

}
