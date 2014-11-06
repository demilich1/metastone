package net.pferdimanzug.hearthstone.analyzer.gui.playmode;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;

public class GameContextVisualizable extends GameContext {
	
	private final List<GameEvent> gameEvents = new ArrayList<>();

	public GameContextVisualizable(Player player1, Player player2, GameLogic logic) {
		super(player1, player2, logic);
	}

	@Override
	public void fireGameEvent(GameEvent gameEvent) {
		super.fireGameEvent(gameEvent);
		
		getGameEvents().add(gameEvent);
	}

	@Override
	protected void onGameStateChanged() {
		if (ignoreEvents()) {
			return;
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		ApplicationFacade.getInstance().sendNotification(GameNotification.GAME_STATE_UPDATE, this);
	}

	public List<GameEvent> getGameEvents() {
		return gameEvents;
	}
	
	
	
	

}
