package net.pferdimanzug.hearthstone.analyzer.game.events;

public interface IGameEventListener {

	public GameEventType interestedIn();
	public void onGameEvent(IGameEvent event);
}
