package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;

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
