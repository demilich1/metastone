package net.pferdimanzug.hearthstone.analyzer.game;

import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventManager;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEventManager;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameResult;
import net.pferdimanzug.hearthstone.analyzer.game.logic.IGameLogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameContext {

	private static Logger logger = LoggerFactory.getLogger(GameContext.class);

	private final Player player1;
	private final Player player2;
	private final IGameLogic logic;
	private final IGameEventManager eventManager = new GameEventManager();

	private Player activePlayer;
	private Player winner;
	private GameResult result;

	private int turn;
	public GameContext(Player player1, Player player2, IGameLogic logic) {
		this.player1 = player1;
		this.player2 = player2;
		this.logic = logic;
	}

	private boolean gameDecided() {
		result = logic.getMatchResult(activePlayer, getOpponent(activePlayer));
		return result != GameResult.RUNNING;
	}

	public IGameEventManager getEventManager() {
		return eventManager;
	}

	public IGameLogic getLogic() {
		return logic;
	}

	public Player getOpponent(Player player) {
		return player == player1 ? player2 : player1;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public GameResult getResult() {
		return result;
	}

	public Player getWinner() {
		return winner;
	}

	public void play() {
		logger.debug("Game starts: " + player1.getName() + " VS. " + player2.getName());
		activePlayer = logic.determineBeginner(player1, player2);
		logger.debug(activePlayer.getName() + " begins");
		logic.init(activePlayer, true);
		logic.init(getOpponent(activePlayer), false);
		while (!gameDecided()) {
			playTurn(activePlayer);
		}
		winner = result == GameResult.WIN ? activePlayer : getOpponent(activePlayer);
		logger.debug("Game finished after " + turn + " turns, the winner is: " + winner.getName());
	}

	private void playTurn(Player player) {
		turn++;
		logic.startTurn(player);
		GameAction nextAction = null;
		while ((nextAction = player.getBehaviour().requestAction(this, logic.getValidActions(player))) != null) {
			logic.performGameAction(player, nextAction);
			
//			ApplicationFacade.getInstance().sendNotification(GameNotification.GAME_STATE_UPDATE, this);
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			if (gameDecided()) {
				return;
			}
		}
		logic.endTurn(player);
		activePlayer = getOpponent(player);
	}

}
