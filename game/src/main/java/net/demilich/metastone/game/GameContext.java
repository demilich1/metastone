package net.demilich.metastone.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.costmodifier.CardCostModifier;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.logic.MatchResult;
import net.demilich.metastone.game.logic.TargetLogic;
import net.demilich.metastone.game.spells.trigger.IGameEventListener;
import net.demilich.metastone.game.spells.trigger.TriggerLayer;
import net.demilich.metastone.game.spells.trigger.TriggerManager;
import net.demilich.metastone.game.targeting.CardReference;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.utils.IDisposable;

public class GameContext implements Cloneable, IDisposable {
	public static final int PLAYER_1 = 0;
	public static final int PLAYER_2 = 1;

	private static final Logger logger = LoggerFactory.getLogger(GameContext.class);

	private final Player[] players = new Player[2];
	private final GameLogic logic;
	private final DeckFormat deckFormat;
	private final TargetLogic targetLogic = new TargetLogic();
	private TriggerManager triggerManager = new TriggerManager();
	private final HashMap<Environment, Object> environment = new HashMap<>();
	private final List<CardCostModifier> cardCostModifiers = new ArrayList<>();

	protected int activePlayer = -1;
	private Player winner;
	private MatchResult result;
	private TurnState turnState = TurnState.TURN_ENDED;

	private int turn;
	private int actionsThisTurn;

	private boolean ignoreEvents;

	public GameContext(Player player1, Player player2, GameLogic logic, DeckFormat deckFormat) {
		this.getPlayers()[PLAYER_1] = player1;
		player1.setId(PLAYER_1);
		this.getPlayers()[PLAYER_2] = player2;
		player2.setId(PLAYER_2);
		this.logic = logic;
		this.deckFormat = deckFormat;
		this.logic.setContext(this);
	}

	protected boolean acceptAction(GameAction nextAction) {
		return true;
	}

	public void addCardCostModifier(CardCostModifier cardCostModifier) {
		getCardCostModifiers().add(cardCostModifier);
	}

	public void addTrigger(IGameEventListener trigger) {
		triggerManager.addTrigger(trigger);
	}

	@Override
	public GameContext clone() {
		GameLogic logicClone = getLogic().clone();
		Player player1Clone = getPlayer1().clone();
		// player1Clone.getDeck().shuffle();
		Player player2Clone = getPlayer2().clone();
		// player2Clone.getDeck().shuffle();
		GameContext clone = new GameContext(player1Clone, player2Clone, logicClone, deckFormat);
		clone.triggerManager = triggerManager.clone();
		clone.activePlayer = activePlayer;
		clone.turn = turn;
		clone.actionsThisTurn = actionsThisTurn;
		clone.result = result;
		clone.turnState = turnState;
		clone.winner = logicClone.getWinner(player1Clone, player2Clone);
		clone.cardCostModifiers.clear();
		for (CardCostModifier cardCostModifier : cardCostModifiers) {
			clone.cardCostModifiers.add(cardCostModifier.clone());
		}
		for (Environment key : getEnvironment().keySet()) {
			clone.getEnvironment().put(key, getEnvironment().get(key));
		}
		clone.getLogic().setLoggingEnabled(false);
		return clone;
	}

	@Override
	public void dispose() {
		for (int i = 0; i < players.length; i++) {
			players[i] = null;
		}
		getCardCostModifiers().clear();
		triggerManager.dispose();
		environment.clear();
	}

	private void endGame() {
		winner = logic.getWinner(getActivePlayer(), getOpponent(getActivePlayer()));
		for (Player player : getPlayers()) {
			player.getBehaviour().onGameOver(this, player.getId(), winner != null ? winner.getId() : -1);
		}

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
		logic.endTurn(activePlayer);
		activePlayer = activePlayer == PLAYER_1 ? PLAYER_2 : PLAYER_1;
		onGameStateChanged();
		turnState = TurnState.TURN_ENDED;
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
		fireGameEvent(gameEvent, TriggerLayer.SECRET);
		fireGameEvent(gameEvent, TriggerLayer.DEFAULT);
	}

	public void fireGameEvent(GameEvent gameEvent, TriggerLayer layer) {
		if (ignoreEvents()) {
			return;
		}
		gameEvent.setTriggerLayer(layer);
		try {
			triggerManager.fireGameEvent(gameEvent);	
		} catch(Exception e) {
			logger.error("Error while processing gameEvent {}", gameEvent);
			logic.panicDump();
			throw e;
		}
		
	}

	public boolean gameDecided() {
		result = logic.getMatchResult(getActivePlayer(), getOpponent(getActivePlayer()));
		winner = logic.getWinner(getActivePlayer(), getOpponent(getActivePlayer()));
		return result != MatchResult.RUNNING;
	}

	public Player getActivePlayer() {
		return getPlayer(activePlayer);
	}

	public int getActivePlayerId() {
		return activePlayer;
	}

	public List<Actor> getAdjacentMinions(Player player, EntityReference minionReference) {
		List<Actor> adjacentMinions = new ArrayList<>();
		Actor minion = (Actor) resolveSingleTarget(minionReference);
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

	public int getBoardPosition(Minion minion) {
		for (Player player : getPlayers()) {
			List<Minion> minions = player.getMinions();
			for (int i = 0; i < minions.size(); i++) {
				if (minions.get(i) == minion) {
					return i;
				}
			}
		}
		return -1;
	}

	public List<CardCostModifier> getCardCostModifiers() {
		return cardCostModifiers;
	}
	
	@SuppressWarnings("unchecked")
	public Stack<Integer> getDamageStack() {
		if (!environment.containsKey(Environment.DAMAGE_STACK)) {
			environment.put(Environment.DAMAGE_STACK, new Stack<Integer>());
		}
		return (Stack<Integer>) environment.get(Environment.DAMAGE_STACK);
	}

	public DeckFormat getDeckFormat() {
		return deckFormat;
	}

	public HashMap<Environment, Object> getEnvironment() {
		return environment;
	}

	@SuppressWarnings("unchecked")
	public Stack<EntityReference> getEventTargetStack() {
		if (!environment.containsKey(Environment.EVENT_TARGET_REFERENCE_STACK)) {
			environment.put(Environment.EVENT_TARGET_REFERENCE_STACK, new Stack<EntityReference>());
		}
		return (Stack<EntityReference>) environment.get(Environment.EVENT_TARGET_REFERENCE_STACK);
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
	
	public Card getPendingCard() {
		return (Card) resolveSingleTarget((EntityReference) getEnvironment().get(Environment.PENDING_CARD));
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
	public Stack<EntityReference> getSummonReferenceStack() {
		if (!environment.containsKey(Environment.SUMMON_REFERENCE_STACK)) {
			environment.put(Environment.SUMMON_REFERENCE_STACK, new Stack<EntityReference>());
		}
		return (Stack<EntityReference>) environment.get(Environment.SUMMON_REFERENCE_STACK);
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

	public TurnState getTurnState() {
		return turnState;
	}

	public List<GameAction> getValidActions() {
		if (gameDecided()) {
			return new ArrayList<>();
		}
		return logic.getValidActions(activePlayer);
	}

	public int getWinningPlayerId() {
		return winner == null ? -1 : winner.getId();
	}

	public boolean ignoreEvents() {
		return ignoreEvents;
	}

	public void init() {
		int startingPlayerId = logic.determineBeginner(PLAYER_1, PLAYER_2);
		activePlayer = getPlayer(startingPlayerId).getId();
		logger.debug(getActivePlayer().getName() + " begins");
		logic.init(activePlayer, true);
		logic.init(getOpponent(getActivePlayer()).getId(), false);
	}

	protected void onGameStateChanged() {
	}

	private void performAction(int playerId, GameAction gameAction) {
		logic.performGameAction(playerId, gameAction);
		onGameStateChanged();
	}

	public void play() {
		logger.debug("Game starts: " + getPlayer1().getName() + " VS. " + getPlayer2().getName());
		init();
		while (!gameDecided()) {
			startTurn(activePlayer);
			while (playTurn()) {}
			if (getTurn() > GameLogic.TURN_LIMIT) {
				break;
			}
		}
		endGame();

	}

	public boolean playTurn() {
		if (++actionsThisTurn > 99) {
			logger.warn("Turn has been forcefully ended after {} actions", actionsThisTurn);
			endTurn();
			return false;
		}

		List<GameAction> validActions = getValidActions();
		if (validActions.size() == 0) {
			endTurn();
			return false;
		}

		GameAction nextAction = getActivePlayer().getBehaviour().requestAction(this, getActivePlayer(), getValidActions());
		while (!acceptAction(nextAction)) {
			nextAction = getActivePlayer().getBehaviour().requestAction(this, getActivePlayer(), getValidActions());
		}
		if (nextAction == null) {
			throw new RuntimeException("Behaviour " + getActivePlayer().getBehaviour().getName() + " selected NULL action while "
					+ getValidActions().size() + " actions were available");
		}
		performAction(activePlayer, nextAction);

		return nextAction.getActionType() != ActionType.END_TURN;
	}

	public void printCurrentTriggers() {
		logger.info("Active spelltriggers:");
		triggerManager.printCurrentTriggers();
	}
	
	public void removeTrigger(IGameEventListener trigger) {
		triggerManager.removeTrigger(trigger);
	}

	public void removeTriggersAssociatedWith(EntityReference entityReference) {
		triggerManager.removeTriggersAssociatedWith(entityReference);
	}

	public Card resolveCardReference(CardReference cardReference) {
		Player player = getPlayer(cardReference.getPlayerId());
		if (getPendingCard() != null && getPendingCard().getCardReference().equals(cardReference)) {
			return getPendingCard();
		}
		switch (cardReference.getLocation()) {
		case DECK:
			return findCardinCollection(player.getDeck(), cardReference.getCardId());
		case HAND:
			return findCardinCollection(player.getHand(), cardReference.getCardId());
		case PENDING:
			return getPendingCard();
		case HERO_POWER:
			return player.getHero().getHeroPower();
		default:
			break;

		}
		logger.error("Could not resolve cardReference {}", cardReference);
		new RuntimeException().printStackTrace();
		return null;
	}

	public Entity resolveSingleTarget(EntityReference targetKey) {
		if (targetKey == null) {
			return null;
		}
		return targetLogic.findEntity(this, targetKey);
	}

	public List<Entity> resolveTarget(Player player, Entity source, EntityReference targetKey) {
		return targetLogic.resolveTargetKey(this, player, source, targetKey);
	}

	public void setIgnoreEvents(boolean ignoreEvents) {
		this.ignoreEvents = ignoreEvents;
	}
	
	public void setPendingCard(Card pendingCard) {
		if (pendingCard != null) {
			getEnvironment().put(Environment.PENDING_CARD, pendingCard.getReference());
		} else {
			getEnvironment().put(Environment.PENDING_CARD, null);
		}
	}

	protected void startTurn(int playerId) {
		turn++;
		logic.startTurn(playerId);
		onGameStateChanged();
		actionsThisTurn = 0;
		turnState = TurnState.TURN_IN_PROGRESS;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("GameContext hashCode: " + hashCode() + "\nPlayer: ");
		for (Player player : players) {
			builder.append(player.getName());
			builder.append(" Mana: ");
			builder.append(player.getMana());
			builder.append('/');
			builder.append(player.getMaxMana());
			builder.append(" HP: ");
			builder.append(player.getHero().getHp() + "(" + player.getHero().getArmor() + ")");
			builder.append('\n');
			builder.append("Behaviour: " + player.getBehaviour().getName() + "\n");
			builder.append("Minions:\n");
			for (Actor minion : player.getMinions()) {
				builder.append('\t');
				builder.append(minion);
				builder.append('\n');
			}
			builder.append("Cards (hand):\n");
			for (Card card : player.getHand()) {
				builder.append('\t');
				builder.append(card);
				builder.append('\n');
			}
			builder.append("Secrets:\n");
			for (String secretId : player.getSecrets()) {
				builder.append('\t');
				builder.append(secretId);
				builder.append('\n');
			}
		}
		builder.append("Turn: " + getTurn() + "\n");
		builder.append("Result: " + result + "\n");
		builder.append("Winner: " + (winner == null ? "tbd" : winner.getName()));

		return builder.toString();
	}

	public Entity tryFind(EntityReference targetKey) {
		try {
			return resolveSingleTarget(targetKey);
		} catch (Exception e) {
		}
		return null;
	}
}
