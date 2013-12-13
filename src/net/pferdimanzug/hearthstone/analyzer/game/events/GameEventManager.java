package net.pferdimanzug.hearthstone.analyzer.game.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameEventManager implements IGameEventManager {
	
	private final HashMap<GameEventType, List<IGameEventListener>> listeners = new HashMap<>();

	@Override
	public void fireGameEvent(IGameEvent event) {
		if (!listeners.containsKey(event.getEventType())) {
			return;
		}
		for (IGameEventListener eventListener : getListSnapshot(event.getEventType())) {
			eventListener.onGameEvent(event);
		}
	}
	
	private List<IGameEventListener> getListSnapshot(GameEventType eventType) {
		List<IGameEventListener> snapshot = new ArrayList<IGameEventListener>();
		snapshot.addAll(listeners.get(eventType));
		return snapshot;
	}

	@Override
	public void registerGameEventListener(IGameEventListener eventListener) {
		GameEventType eventType = eventListener.interestedIn();
		if (!listeners.containsKey(eventType)) {
			listeners.put(eventType, new ArrayList<IGameEventListener>());
		}
		listeners.get(eventType).add(eventListener);
	}

	@Override
	public void removeGameEventListener(IGameEventListener eventListener) {
		GameEventType eventType = eventListener.interestedIn();
		if (!listeners.containsKey(eventType)) {
			return;
		}
		listeners.get(eventType).remove(eventListener);
	}

}
