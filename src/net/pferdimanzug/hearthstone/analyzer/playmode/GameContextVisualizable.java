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
import net.pferdimanzug.hearthstone.analyzer.game.logic.IGameLogic;

public class GameContextVisualizable extends GameContext {
	
	private final HashMap<GameAction, List<GameEvent>> turnEvents = new HashMap<>();
	private GameAction currentAction;

	public GameContextVisualizable(Player player1, Player player2, IGameLogic logic) {
		super(player1, player2, logic);
	}

	@Override
	protected void onGameStateChanged() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		ApplicationFacade.getInstance().sendNotification(GameNotification.GAME_STATE_UPDATE, this);
	}
	
	@Override
	protected void onActionPerform(GameAction action) {
		if (!turnEvents.containsKey(action)) {
			turnEvents.put(action, new ArrayList<GameEvent>());
		}
		setCurrentAction(action);
		ApplicationFacade.getInstance().sendNotification(GameNotification.GAME_ACTION_PERFORMED, this);
	}

	@Override
	public void fireGameEvent(GameEvent gameEvent) {
		super.fireGameEvent(gameEvent);
		
		if (currentAction == null) {
			return;
		}
		
		List<GameEvent> eventList = turnEvents.get(getCurrentAction());
		eventList.add(gameEvent);
	}
	
	public List<GameEvent> getEventsForTurn(int turn) {
		return turnEvents.get(turn);
	}

	public GameAction getCurrentAction() {
		return currentAction;
	}

	public void setCurrentAction(GameAction currentAction) {
		this.currentAction = currentAction;
	}
	
	
	
	

}
