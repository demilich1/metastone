package net.pferdimanzug.hearthstone.analyzer.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import net.pferdimanzug.hearthstone.analyzer.game.actions.EndTurnAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier.CardCostModifier;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.game.logic.MatchResult;
import net.pferdimanzug.hearthstone.analyzer.game.logic.TargetLogic;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.IGameEventListener;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TriggerLayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TriggerManager;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameContext implements Cloneable {
	public static final int PLAYER_1 = 0;
	public static final int PLAYER_2 = 1;
	
	private static final Logger logger = LoggerFactory.getLogger(GameContext.class);

	private final Player[] players = new Player[2];
	private final GameLogic logic;
	private final TargetLogic targetLogic = new TargetLogic();
	private TriggerManager triggerManager = new TriggerManager();
	private final HashMap<Environment, Object> environment = new HashMap<>();
	private final List<CardCostModifier> cardCostModifiers = new ArrayList<>();
	
	protected Player activePlayer;
	private Player winner;

	private int turn;
	private int actionsThisTurn;

	public GameContext(Player player1, Player player2, GameLogic logic) {
		this.getPlayers()[PLAYER_1] = player1;
		player1.setId(PLAYER_1);
		this.getPlayers()[PLAYER_2] = player2;
		player2.setId(PLAYER_2);
		this.logic = logic;
		this.logic.setContext(this);
	}

	public void addCardCostModfier(CardCostModifier cardCostModifier) {
		getCardCostModifiers().add(cardCostModifier);
	}

	public void addTrigger(IGameEventListener spellTrigger) {
		triggerManager.addTrigger(spellTrigger);
	}

	@Override
	public GameContext clone() {
		GameLogic logicClone = getLogic().clone();
		Player player1Clone = getPlayer1().clone();
		Player player2Clone = getPlayer2().clone();
		GameContext clone = new GameContext(player1Clone, player2Clone, logicClone);
		clone.triggerManager = triggerManager.clone();
		clone.activePlayer = activePlayer == getPlayer1() ? player1Clone : player2Clone;
		clone.turn = turn;
		clone.actionsThisTurn = actionsThisTurn;
		clone.winner = logicClone.getWinner(player1Clone, player2Clone);
		clone.cardCostModifiers.clear();
		for (CardCostModifier cardCostModifier : cardCostModifiers) {
			clone.cardCostModifiers.add(cardCostModifier);
		}
		for (Environment key : getEnvironment().keySet()) {
			clone.getEnvironment().put(key, getEnvironment().get(key));
		}
		return clone;
	}

	private Card findCardinCollection(CardCollection cardCollection, int cardId) {
		for (Card card : cardCollection) {
			if (card.getId() == cardId) {
				return card;
			}
		}
		return null;
	}

	public void fireGameEvent(GameEvent gameEvent) {
		fireGameEvent(gameEvent, TriggerLayer.DEFAULT);
	}

	public void fireGameEvent(GameEvent gameEvent, TriggerLayer layer) {
		gameEvent.setTriggerLayer(layer);
		triggerManager.fireGameEvent(gameEvent);
	}

	public boolean gameDecided() {
		MatchResult result = logic.getMatchResult(activePlayer, getOpponent(activePlayer));
		return result != MatchResult.RUNNING;
	}
	
	public Player getActivePlayer() {
		return activePlayer;
	}
	
	public List<Entity> getAdjacentMinions(Player player, EntityReference minionReference) {
		List<Entity> adjacentMinions = new ArrayList<>();
		Actor minion = (Actor) resolveSingleTarget(player.getId(), minionReference);
		List<Minion> minions = getPlayer(minion.getOwner()).getMinions();
		int index = minions.indexOf(minion);
		if (index == -1) {
			return null;
		}
		int left = index - 1;
		int right = index + 1;
		if (left > -1 && left < minions.size()) {
			adjacentMinions.add(minions.get(left));
		}
		if (right > -1 && right < minions.size()) {
			adjacentMinions.add(minions.get(right));
		}
		return adjacentMinions;
	}
	
	public List<CardCostModifier> getCardCostModifiers() {
		return cardCostModifiers;
	}
	
	public HashMap<Environment, Object> getEnvironment() {
		return environment;
	}

	public GameLogic getLogic() {
		return logic;
	}

	public int getMinionCount(Player player) {
		return player.getMinions().size();
	}
	
	public Player getOpponent(Player player) {
		return player.getId() == PLAYER_1 ? getPlayer2() : getPlayer1();
	}
	
	public Player getPlayer(int index) {
		return players[index];
	}

	public Player getPlayer1() {
		return getPlayers()[PLAYER_1];
	}
	
	public Player getPlayer2() {
		return getPlayers()[PLAYER_2];
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	@SuppressWarnings("unchecked")
	public Stack<Minion> getSummonStack() {
		if (!environment.containsKey(Environment.SUMMON_STACK)) {
			environment.put(Environment.SUMMON_STACK, new Stack<Minion>());
		}
		return (Stack<Minion>) environment.get(Environment.SUMMON_STACK);
	}
	
	public int getTotalMinionCount() {
		int totalMinionCount = 0;
		for (int i = 0; i < players.length; i++) {
			totalMinionCount += getMinionCount(players[i]);
		}
		return totalMinionCount;
	}

	public List<IGameEventListener> getTriggersAssociatedWith(EntityReference entityReference) {
		return triggerManager.getTriggersAssociatedWith(entityReference);
	}
	
	public int getTurn() {
		return turn;
	}

	public boolean isWinner(int playerId) {
		if (winner == null) {
			return false;
		}
		return winner.getId() == playerId;
	}

	protected void onGameStateChanged() {	
	}
	
	protected void onWillPerformAction(GameAction action) {
	}
	
	public void play() {
		logger.debug("Game starts: " + getPlayer1().getName() + " VS. " + getPlayer2().getName());
		int startingPlayerId = logic.determineBeginner(PLAYER_1, PLAYER_2);
		activePlayer = getPlayer(startingPlayerId);
		logger.debug(activePlayer.getName() + " begins");
		logic.init(activePlayer.getId(), true);
		logic.init(getOpponent(activePlayer).getId(), false);
		startTurn(activePlayer);
	}

	private void startTurn(Player player) {
		turn++;
		logic.startTurn(player.getId());
		onGameStateChanged();
		actionsThisTurn = 0;
		playTurn(player);
		/*
		GameAction nextAction = player.getBehaviour().requestAction(this, player, logic.getValidActions(player.getId()));
		actionsThisTurn = 0;
		while (nextAction != null) {
			onWillPerformAction(nextAction);
			logic.performGameAction(player.getId(), nextAction);
			onGameStateChanged();
			if (gameDecided()) {
				return;
			}
			if (++actionsThisTurn > 99) {
				logger.warn("Turn has been forcefully ended after {} actions", actionsThisTurn);
				break;
			}
			nextAction = player.getBehaviour().requestAction(this, player, logic.getValidActions(player.getId()));
		}
		*/
		if (!gameDecided()) {
			onWillPerformAction(new EndTurnAction());
			logic.endTurn(player.getId());
			activePlayer = getOpponent(player);	
		}
	}
	
	private void endGame() {
		winner = logic.getWinner(activePlayer, getOpponent(activePlayer));
		if (winner != null) {
			logger.debug("Game finished after " + turn + " turns, the winner is: " + winner.getName());
			winner.getStatistics().gameWon();
			Player looser = getOpponent(winner);
			looser.getStatistics().gameLost();
		} else {
			logger.debug("Game finished after " + turn + " turns, DRAW");
			getPlayer1().getStatistics().gameLost();
			getPlayer2().getStatistics().gameLost();
		}
	}
	
	public void playTurn(Player player) {
		GameAction nextAction = player.getBehaviour().requestAction(this, player, logic.getValidActions(player.getId()));
		while (nextAction != null) {
			onWillPerformAction(nextAction);
			logic.performGameAction(player.getId(), nextAction);
			onGameStateChanged();
			if (gameDecided()) {
				endGame();
				return;
			}
			if (++actionsThisTurn > 99) {
				logger.warn("Turn has been forcefully ended after {} actions", actionsThisTurn);
				break;
			}
			nextAction = player.getBehaviour().requestAction(this, player, logic.getValidActions(player.getId()));
		}
		onWillPerformAction(new EndTurnAction());
		logic.endTurn(player.getId());
		activePlayer = getOpponent(player);
		startTurn(activePlayer);
	}

	public void removeTriggersAssociatedWith(EntityReference entityReference) {
		triggerManager.removeTriggersAssociatedWith(entityReference);
	}
	
	public Card resolveCardReference(CardReference cardReference) {
		Player player = getPlayer(cardReference.getPlayerId());
		switch (cardReference.getLocation()) {
		case DECK:
			return findCardinCollection(player.getDeck(), cardReference.getCardId());
		case HAND:
			return findCardinCollection(player.getHand(), cardReference.getCardId());
		case PENDING:
			return (Card)getEnvironment().get(Environment.PENDING_CARD);
		case HERO_POWER:
			return player.getHero().getHeroPower();
		default:
			break;
		
		}
		logger.error("Could not resolve cardReference [}", cardReference);
		return null;
	}

	public Entity resolveSingleTarget(int playerId, EntityReference targetKey) {
		Player player = getPlayer(playerId);
		return targetLogic.resolveTargetKey(this, player, null, targetKey).get(0);
	}
	
	public List<Entity> resolveTarget(Player player, Actor source, EntityReference targetKey) {
		return targetLogic.resolveTargetKey(this, player, source, targetKey);
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
			result.append(" HP: ");
			result.append(player.getHero().getHp() + "(" + player.getHero().getArmor() + ")");
			result.append('\n');
			result.append("Minions:\n");
			for (Actor minion : player.getMinions()) {
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
