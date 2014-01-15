package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;

public class TurnEndEvent extends GameEvent {

	private final int playerIndex;

	public TurnEndEvent(GameContext context, int playerIndex) {
		super(context);
		this.playerIndex = playerIndex;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.TURN_END;
	}

	public int getPlayer() {
		return playerIndex;
	}

}
