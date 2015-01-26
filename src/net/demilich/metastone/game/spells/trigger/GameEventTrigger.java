package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.logic.CustomCloneable;

public abstract class GameEventTrigger extends CustomCloneable {

	private int owner = -1;

	@Override
	public GameEventTrigger clone() {
		return (GameEventTrigger) super.clone();
	}

	public abstract boolean fire(GameEvent event, Entity host);

	public int getOwner() {
		return owner;
	}

	public abstract GameEventType interestedIn();
	
	public void setOwner(int playerIndex) {
		this.owner = playerIndex;
	}

	@Override
	public String toString() {
		return "[" + getClass().getSimpleName() + " owner:" + owner + "]";
	}

}
