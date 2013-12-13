package net.pferdimanzug.hearthstone.analyzer.game.events;

public interface IGameEventManager {
	
	public void fireGameEvent(IGameEvent event);
	public void registerGameEventListener(IGameEventListener eventListener);
	public void removeGameEventListener(IGameEventListener eventListener);

}
