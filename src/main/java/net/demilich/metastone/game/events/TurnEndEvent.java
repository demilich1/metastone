package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;

public class TurnEndEvent extends GameEvent {

	private final int playerIndex;

	public TurnEndEvent(GameContext context, int playerIndex) {
		super(context);
		this.playerIndex = playerIndex;
	}

	@Override
	public Entity getEventTarget() {
		return null;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.TURN_END;
	}

	public int getPlayer() {
		return playerIndex;
	}

}
