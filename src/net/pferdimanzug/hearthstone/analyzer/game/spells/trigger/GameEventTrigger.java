package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;

public abstract class GameEventTrigger {

	private int owner;

	public abstract boolean fire(IGameEvent event, Entity host);

	public abstract GameEventType interestedIn();

	public int getOwner() {
		return owner;
	}

	public void setOwner(int playerIndex) {
		this.owner = playerIndex;
	}

}
