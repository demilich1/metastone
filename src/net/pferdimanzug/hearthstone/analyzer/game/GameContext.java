package net.pferdimanzug.hearthstone.analyzer.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
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
	
	protected int activePlayer;
	private Player winner;
	private MatchResult result;

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
		player1Clone.getDeck().shuffle();
		Player player2Clone = getPlayer2().clone();
		player2Clone.getDeck().shuffle();
		GameContext clone = new GameContext(player1Clone, player2Clone, logicClone);
		clone.triggerManager = triggerManager.clone();
		clone.activePlayer = activePlayer;
		clone.turn = turn;
		clone.actionsThisTurn = actionsThisTurn;
		clone.result = result;
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

	private void endGame() {
		winner = logic.getWinner(getActivePlayer(), getOpponent(getActivePlayer()));
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

	public void endTurn() {
		onWillPerformAction(new EndTurnAction());
		logic.endTurn(activePlayer);
		activePlayer = activePlayer == PLAYER_1 ? PLAYER_2 : PLAYER_1;
		onGameStateChanged();
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
		result = logic.getMatchResult(getActivePlayer(), getOpponent(getActivePlayer()));
		winner = logic.getWinner(getActivePlayer(), getOpponent(getActivePlayer()));
		return result != MatchResult.RUNNING;
	}
	
	public Player getActivePlayer() {
		return getPlayer(activePlayer);
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

	public int getScore(int playerId) {
		switch (result) {
		case DOUBLE_LOSS:
			return 0;
		case RUNNING:
			throw new IllegalStateException("Score cannot be determined, game still running");
		case WON:
			return winner.getId() == playerId ? 1 : 0;
		default:
			break;
		}
		throw new IllegalStateException("Invalid match result: " + result);
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

	public List<GameAction> getValidActions() {
		if (gameDecided()) {
			return new ArrayList<>();
		}
		return logic.getValidActions(activePlayer);
	}
	
	protected void onGameStateChanged() {	
	}
	
	protected void onWillPerformAction(GameAction action) {
	}
	
	private void performAction(int playerId, GameAction gameAction) {
		onWillPerformAction(gameAction);
		logic.performGameAction(playerId, gameAction);
		onGameStateChanged();
	}
	
	public void play() {
		logger.debug("Game starts: " + getPlayer1().getName() + " VS. " + getPlayer2().getName());
		int startingPlayerId = logic.determineBeginner(PLAYER_1, PLAYER_2);
		activePlayer = getPlayer(startingPlayerId).getId();
		logger.debug(getActivePlayer().getName() + " begins");
		logic.init(activePlayer, true);
		logic.init(getOpponent(getActivePlayer()).getId(), false);
		startTurn(activePlayer);
	}

	public void playTurn() {
		if (++actionsThisTurn > 99) {
			logger.warn("Turn has been forcefully ended after {} actions", actionsThisTurn);
			startTurn(getOpponent(getActivePlayer()).getId());
			return;
		}
		
		if (gameDecided()) {
			endGame();
			return;
		}
		
		GameAction nextAction = getActivePlayer().getBehaviour().requestAction(this, getActivePlayer(), getValidActions());
		performAction(activePlayer, nextAction);
		
		if (nextAction.getActionType() != ActionType.END_TURN) {
			playTurn();
		} else {
			startTurn(activePlayer);
		}
		
		/*
		GameAction nextAction = player.getBehaviour().requestAction(this, player, logic.getValidActions(player.getId()));
		while (nextAction != null && player == activePlayer) {
			performAction(player.getId(), nextAction);
			if (++actionsThisTurn > 99) {
				logger.warn("Turn has been forcefully ended after {} actions", actionsThisTurn);
				return;
			}
			if (gameDecided()) {
				endGame();
				return;
			}
			nextAction = player.getBehaviour().requestAction(this, player, logic.getValidActions(player.getId()));
		}*/
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
		if (targetKey == null) {
			return null;
		}
		Player player = getPlayer(playerId);
		return targetLogic.resolveTargetKey(this, player, null, targetKey).get(0);
	}
	
	public List<Entity> resolveTarget(Player player, Actor source, EntityReference targetKey) {
		return targetLogic.resolveTargetKey(this, player, source, targetKey);
	}
	
	public void startTurn(int playerId) {
		turn++;
		logic.startTurn(playerId);
		onGameStateChanged();
		actionsThisTurn = 0;
		playTurn();
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
