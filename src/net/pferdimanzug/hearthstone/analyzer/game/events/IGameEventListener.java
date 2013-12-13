package net.pferdimanzug.hearthstone.analyzer.game.events;

public interface IGameEventListener {

	public void onGameEvent(IGameEvent event);
	public GameEventType interestedIn();
}
