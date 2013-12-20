package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;

public interface IGameEventTrigger {
	
	boolean fire(IGameEvent event, Entity host);
	GameEventType interestedIn();
	Entity getTarget(GameContext context, Entity host);

}
