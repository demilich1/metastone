package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;

public interface IGameEvent {
	public GameEventType getEventType();
	public GameContext getGameContext();
}
