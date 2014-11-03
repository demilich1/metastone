package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.logic.CustomCloneable;

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
