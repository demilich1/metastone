package net.pferdimanzug.hearthstone.analyzer.game;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventManager;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEventManager;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameResult;
import net.pferdimanzug.hearthstone.analyzer.game.logic.IGameLogic;
import net.pferdimanzug.hearthstone.analyzer.game.logic.TargetLogic;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rits.cloning.Cloner;

public class GameContext implements Cloneable {

	private static final Logger logger = LoggerFactory.getLogger(GameContext.class);
	private static final Cloner cloner = new Cloner();

	private final Player[] players = new Player[2];
	private final IGameLogic logic;
	private final TargetLogic targetLogic = new TargetLogic();
	private final IGameEventManager eventManager = new GameEventManager();
	// this list is most of the time empty. Whenever a minion is summoned,
	// it is placed in this list for brief amount of time
	// reason is Battlecry effects may target not yet summoned
	// minions and because of the lazy references to entities
	// we cannot find the to-be-summoned minion if its Battlecry
	// tries to target itself
	private final List<Entity> pendingEntities = new ArrayList<Entity>();

	private Player activePlayer;
	private Player winner;
	private GameResult result;

	private int turn;
	
	static {
		cloner.dontClone(TargetKey.class);
	}

	public GameContext(Player player1, Player player2, IGameLogic logic) {
		this.getPlayers()[0] = player1;
		player1.setId(0);
		this.getPlayers()[1] = player2;
		player2.setId(1);
		this.logic = logic;
		this.logic.setContext(this);
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
		return player.getId() == 0 ? getPlayer2() : getPlayer1();
	}

	public Player getPlayer1() {
		return getPlayers()[0];
	}

	public Player getPlayer2() {
		return getPlayers()[1];
	}

	public Player getPlayer(int index) {
		return players[index];
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
		while ((nextAction = player.getBehaviour().requestAction(this, player, logic.getValidActions(player))) != null) {
			logic.performGameAction(player.getId(), nextAction);

			// ApplicationFacade.getInstance().sendNotification(GameNotification.GAME_STATE_UPDATE,
			// this);
			// try {
			// Thread.sleep(1000);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
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

	public GameContext clone() {
		GameContext clone = cloner.deepClone(this);
		clone.getLogic().setContext(clone);
		return clone;
	}

	public int getTurn() {
		return turn;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Player player : players) {
			result.append(player.getName());
			result.append(" Mana: ");
			result.append(player.getMana());
			result.append('/');
			result.append(player.getMaxMana());
			result.append('\n');
			result.append("Minions:\n");
			for (Minion minion : player.getMinions()) {
				result.append('\t');
				result.append(minion);
				result.append('\n');
			}
			result.append("Cards (hand):\n");
			for (Card card : player.getHand()) {
				result.append('\t');
				result.append(card);
				result.append('\n');
			}
		}
		result.append("Turn: " + getTurn());

		return result.toString();
	}
}
