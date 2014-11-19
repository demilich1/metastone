package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;

public class TurnEndAndControlSecretTrigger extends TurnEndTrigger {

	@Override
	public boolean fire(GameEvent event, Entity host) {
		if (!super.fire(event, host)) {
			return false;
		}

		Player player = event.getGameContext().getPlayer(host.getOwner());
		return !player.getSecrets().isEmpty();
	}

}