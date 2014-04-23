package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;

public abstract class SecretTrigger extends GameEventTrigger {

	public boolean fire(IGameEvent event, Actor host) {
		GameContext context = event.getGameContext();
		// Secrets can only be triggered on opponents turn
		if (context.getActivePlayer() != context.getPlayer(getOwner())) {
			return false;
		}

		return secretTriggered(event, host);
	}

	protected abstract boolean secretTriggered(IGameEvent event, Actor host);

}
