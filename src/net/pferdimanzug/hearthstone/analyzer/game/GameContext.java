package net.pferdimanzug.hearthstone.analyzer.game;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventManager;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEventManager;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameResult;
import net.pferdimanzug.hearthstone.analyzer.game.logic.IGameLogic;
import net.pferdimanzug.hearthstone.analyzer.game.logic.TargetLogic;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameContext {

	private static Logger logger = LoggerFactory.getLogger(GameContext.class);

	private final Player[] players = new Player[2];
	private final IGameLogic logic;
	private final TargetLogic targetLogic = new TargetLogic();
	private final IGameEventManager eventManager = new GameEventManager();
	// this list is most of the time empty. Whenenver a minion is summoned,
	// it is placed in this list for brief amount of time
	// reason is battlecry effects may target not yet summoned
	// minions and because of the lazy references to entities
	// we cannot find the to-be-summoned minion if its battlecry
	private final List<Entity> pendingEntities = new ArrayList<Entity>();

	private Player activePlayer;
	private Player winner;
	private GameResult result;

	private int turn;
	public GameContext(Player player1, Player player2, IGameLogic logic) {
		this.getPlayers()[0] = player1;
		this.getPlayers()[1] = player2;
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
		return player == getPlayer1() ? getPlayer2() : getPlayer1();
	}

	public Player getPlayer1() {
		return getPlayers()[0];
	}

	public Player getPlayer2() {
		return getPlayers()[1];
	}

	public GameResult getResult() {
		return result;
	}

	public Player getWinner() {
		return winner;
	}

	public void play() {
		logger.debug("Game starts: " + getPlayer1().getName() + " VS. " + getPlayer2().getName());
		activePlayer = logic.determineBeginner(getPlayer1(), getPlayer2());
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

	public Player[] getPlayers() {
		return players;
	}
	
	public List<Entity> resolveTarget(Player player, TargetKey targetKey) {
		return targetLogic.resolveTargetKey(this, player, targetKey);
	}
	
	public Entity resolveSingleTarget(Player player, TargetKey targetKey) {
		return targetLogic.resolveTargetKey(this, player, targetKey).get(0);
	}

	public List<Entity> getPendingEntities() {
		return pendingEntities;
	}

}
