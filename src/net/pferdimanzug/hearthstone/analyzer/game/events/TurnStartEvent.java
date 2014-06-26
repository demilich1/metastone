package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class TurnStartEvent extends GameEvent {

	private final int playerIndex;

	public TurnStartEvent(GameContext context, int playerIndex) {
		super(context);
		this.playerIndex = playerIndex;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.TURN_START;
	}

	public int getPlayer() {
		return playerIndex;
	}

	@Override
	public Entity getEventTarget() {
		return null;
	}

}
