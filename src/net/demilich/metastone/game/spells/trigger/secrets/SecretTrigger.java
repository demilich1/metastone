package net.demilich.metastone.game.spells.trigger.secrets;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;

public class SecretTrigger extends GameEventTrigger {

	private final GameEventTrigger trigger;

	public SecretTrigger(GameEventTrigger trigger) {
		this.trigger = trigger;
	}

	@Override
	public GameEventTrigger clone() {
		GameEventTrigger clone = new SecretTrigger(trigger.clone());
		clone.setOwner(getOwner());
		return clone;
	}

	public boolean fire(GameEvent event, Entity host) {
		GameContext context = event.getGameContext();
		// Secrets can only be triggered on opponents turn
		if (context.getActivePlayerId() == getOwner()) {
			return false;
		}

		return trigger.fire(event, host);
	}

	@Override
	public GameEventType interestedIn() {
		return trigger.interestedIn();
	}
	
	@Override
	public void setOwner(int playerIndex) {
		super.setOwner(playerIndex);
		trigger.setOwner(playerIndex);
	}

}
