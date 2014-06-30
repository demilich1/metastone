package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;

public class GameContextVisualizable extends GameContext {
	
	private final HashMap<GameAction, List<GameEvent>> actionEvents = new HashMap<>();
	private GameAction currentAction;

	public GameContextVisualizable(Player player1, Player player2, GameLogic logic) {
		super(player1, player2, logic);
	}

	@Override
	public void fireGameEvent(GameEvent gameEvent) {
		super.fireGameEvent(gameEvent);
		
		if (currentAction == null) {
			return;
		}
		
		List<GameEvent> eventList = actionEvents.get(currentAction);
		eventList.add(gameEvent);
	}
	
	public GameAction getCurrentAction() {
		return currentAction;
	}

	public List<GameEvent> getEventsForAction(GameAction action) {
		return actionEvents.get(action);
	}
	
	@Override
	protected void onGameStateChanged() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		ApplicationFacade.getInstance().sendNotification(GameNotification.GAME_STATE_UPDATE, this);
	}

	@Override
	protected void onWillPerformAction(GameAction action) {
		if (!actionEvents.containsKey(action)) {
			actionEvents.put(action, new ArrayList<GameEvent>());
		}
		currentAction = action;
		ApplicationFacade.getInstance().sendNotification(GameNotification.GAME_ACTION_PERFORMED, this);
	}

	public void setCurrentAction(GameAction currentAction) {
		this.currentAction = currentAction;
	}
	
	
	
	

}
