package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;

public class TurnStartEvent extends GameEvent {

	private final int playerIndex;

	public TurnStartEvent(GameContext context, int playerIndex) {
		super(context);
		this.playerIndex = playerIndex;
	}

	@Override
	public Entity getEventTarget() {
		return null;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.TURN_START;
	}

	public int getPlayer() {
		return playerIndex;
	}

}
