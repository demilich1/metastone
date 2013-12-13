package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;

public abstract class GameEvent implements IGameEvent {

	private final GameContext context;

	public GameEvent(GameContext context) {
		this.context = context;
	}

	@Override
	public GameContext getGameContext() {
		return context;
	}

}
