package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.logic.IGameLogic;

public class GameContextVisualizable extends GameContext {
	
	private final HashMap<Integer, List<GameEvent>> turnEvents = new HashMap<>();

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
	public void fireGameEvent(GameEvent gameEvent) {
		super.fireGameEvent(gameEvent);
		if (!turnEvents.containsKey(getTurn())) {
			turnEvents.put(getTurn(), new ArrayList<GameEvent>());
		}
		
		List<GameEvent> eventList = turnEvents.get(getTurn());
		eventList.add(gameEvent);
	}
	
	public List<GameEvent> getEventsForTurn(int turn) {
		return turnEvents.get(turn);
	}
	
	
	
	

}
