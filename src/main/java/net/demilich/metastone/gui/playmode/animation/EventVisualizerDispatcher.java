package net.demilich.metastone.gui.playmode.animation;

import java.util.HashMap;

import net.demilich.metastone.ApplicationFacade;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.gui.playmode.GameBoardView;
import net.demilich.metastone.gui.playmode.GameContextVisualizable;

public class EventVisualizerDispatcher {

	private static final HashMap<GameEventType, IGameEventVisualizer> visualizers = new HashMap<GameEventType, IGameEventVisualizer>();

	static {
		visualizers.put(GameEventType.DAMAGE, new DamageEventVisualizer());
		visualizers.put(GameEventType.HEAL, new HealEventVisualizer());
		visualizers.put(GameEventType.PLAY_CARD, new PlayCardVisualizer());
	}

	public void visualize(GameContextVisualizable gameContext, GameBoardView boardView) {
		ApplicationFacade.getInstance().sendNotification(GameNotification.ANIMATION_STARTED);
		for (GameEvent event : gameContext.getGameEvents()) {
			IGameEventVisualizer gameEventVisualizer = visualizers.get(event.getEventType());
			if (gameEventVisualizer == null) {
				continue;
			}
			gameEventVisualizer.visualizeEvent(gameContext, event, boardView);
		}
		gameContext.getGameEvents().clear();
		ApplicationFacade.getInstance().sendNotification(GameNotification.ANIMATION_COMPLETED);
	}

}
