package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class TurnEndAndControlSecretTrigger extends TurnEndTrigger {

	public TurnEndAndControlSecretTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		if (!super.fire(event, host)) {
			return false;
		}

		Player player = event.getGameContext().getPlayer(host.getOwner());
		return !player.getSecrets().isEmpty();
	}

}