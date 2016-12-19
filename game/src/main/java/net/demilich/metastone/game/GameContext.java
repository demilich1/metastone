package net.demilich.metastone.game;

import co.paralleluniverse.fibers.Suspendable;
import com.google.gson.annotations.Expose;
import com.hiddenswitch.proto3.net.common.GameState;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
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
import net.demilich.metastone.game.spells.trigger.TriggerManager;
import net.demilich.metastone.game.targeting.CardReference;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.IdFactory;
import net.demilich.metastone.utils.IDisposable;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;

public class GameContext implements Cloneable, IDisposable, Serializable {
	public static final int PLAYER_1 = 0;
	public static final int PLAYER_2 = 1;

	protected static final Logger logger = LoggerFactory.getLogger(GameContext.class);

	private Player[] players = new Player[2];
	@Expose(serialize = false, deserialize = false)
	private GameLogic logic;
	private DeckFormat deckFormat;
	private TargetLogic targetLogic = new TargetLogic();
	private TriggerManager triggerManager = new TriggerManager();
	private HashMap<Environment, Object> environment = new HashMap<>();
	private List<CardCostModifier> cardCostModifiers = new ArrayList<>();
	private final List<Throwable> exceptions = new ArrayList<>();
	private int activePlayerId = -1;
	private Player winner;
	private MatchResult result;
	private TurnState turnState = TurnState.TURN_ENDED;
	private boolean disposed = false;

	private int turn;
	private int actionsThisTurn;

	private boolean ignoreEvents;

	private CardCollection tempCards = new CardCollection();

	public GameContext() {
	}

	public GameContext(Player player1, Player player2, GameLogic logic, DeckFormat deckFormat) {
		this.setPlayer1(player1);
		if (player1.getId() == IdFactory.UNASSIGNED) {
			player1.setId(PLAYER_1);
		}
		if (player2 != null) {
			this.setPlayer2(player2);
			if (player2.getId() == IdFactory.UNASSIGNED) {
				player2.setId(PLAYER_2);
			}
		}

		this.setLogic(logic);
		this.setDeckFormat(deckFormat);
		getTempCards().removeAll();
	}


	protected boolean acceptAction(GameAction nextAction) {
		return true;
	}

	public void addTempCard(Card card) {
		getTempCards().add(card);
	}

	public void addTrigger(IGameEventListener trigger) {
		getTriggerManager().addTrigger(trigger);
	}

	@Override
	public synchronized GameContext clone() {
		GameLogic logicClone = getLogic().clone();
		Player player1Clone = getPlayer1().clone();
		Player player2Clone = getPlayer2().clone();
		GameContext clone = new GameContext(player1Clone, player2Clone, logicClone, getDeckFormat());
		clone.setTempCards(getTempCards().clone());
		clone.setTriggerManager(getTriggerManager().clone());
		clone.setActivePlayerId(activePlayerId);
		clone.setTurn(getTurn());
		clone.setActionsThisTurn(getActionsThisTurn());
		clone.setResult(getResult());
		clone.setTurnState(getTurnState());
		clone.setWinner(logicClone.getWinner(player1Clone, player2Clone));
		clone.getCardCostModifiers().clear();
		for (CardCostModifier cardCostModifier : getCardCostModifiers()) {
			clone.getCardCostModifiers().add(cardCostModifier.clone());
		}
		Stack<Integer> damageStack = new Stack<Integer>();
		damageStack.addAll(getDamageStack());
		clone.getEnvironment().put(Environment.DAMAGE_STACK, damageStack);
		Stack<EntityReference> summonReferenceStack = new Stack<EntityReference>();
		summonReferenceStack.addAll(getSummonReferenceStack());
		clone.getEnvironment().put(Environment.SUMMON_REFERENCE_STACK, summonReferenceStack);
		Stack<EntityReference> eventTargetReferenceStack = new Stack<EntityReference>();
		eventTargetReferenceStack.addAll(getEventTargetStack());
		clone.getEnvironment().put(Environment.EVENT_TARGET_REFERENCE_STACK, eventTargetReferenceStack);

		for (Environment key : getEnvironment().keySet()) {
			if (!key.customClone()) {
				clone.getEnvironment().put(key, getEnvironment().get(key));
			}
		}

		clone.getLogic().setLoggingEnabled(false);
		return clone;
	}

	@Override
	public synchronized void dispose() {
		this.disposed = true;
		this.players = null;
		getCardCostModifiers().clear();
		getTriggerManager().dispose();
		getEnvironment().clear();
	}

	protected void endGame() {
		setWinner(getLogic().getWinner(getActivePlayer(), getOpponent(getActivePlayer())));

		notifyPlayersGameOver();

		calculateStatistics();
	}

	protected void notifyPlayersGameOver() {
		for (Player player : getPlayers()) {
			player.getBehaviour().onGameOver(this, player.getId(), getWinner() != null ? getWinner().getId() : -1);
		}
	}

	protected void calculateStatistics() {
		if (getWinner() != null) {
			logger.debug("Game finished after " + getTurn() + " turns, the winner is: " + getWinner().getName());
			getWinner().getStatistics().gameWon();
			Player loser = getOpponent(getWinner());
			loser.getStatistics().gameLost();
		} else {
			logger.debug("Game finished after " + getTurn() + " turns, DRAW");
			getPlayer1().getStatistics().gameLost();
			getPlayer2().getStatistics().gameLost();
		}
	}

	public void endTurn() {
		getLogic().endTurn(getActivePlayerId());
		setActivePlayerId(getActivePlayerId() == PLAYER_1 ? PLAYER_2 : PLAYER_1);
		onGameStateChanged();
		setTurnState(TurnState.TURN_ENDED);
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
		if (ignoreEvents()) {
			return;
		}
		try {
			getTriggerManager().fireGameEvent(gameEvent);
		} catch (Exception e) {
			logger.error("Error while processing gameEvent {}", gameEvent);
			getLogic().panicDump();
			throw e;
		}
	}

	public boolean gameDecided() {
		setResult(getLogic().getMatchResult(getActivePlayer(), getOpponent(getActivePlayer())));
		setWinner(getLogic().getWinner(getActivePlayer(), getOpponent(getActivePlayer())));
		return getResult() != MatchResult.RUNNING;
	}

	public Player getActivePlayer() {
		return getPlayer(getActivePlayerId());
	}

	public int getActivePlayerId() {
		return activePlayerId;
	}

	public List<Actor> getAdjacentMinions(Player player, EntityReference minionReference) {
		List<Actor> adjacentMinions = new ArrayList<>();
		Actor minion = (Actor) resolveSingleTarget(minionReference);
		List<Minion> minions = getPlayer(minion.getOwner()).getMinions();
		int index = minions.indexOf(minion);
		if (index == -1) {
			return adjacentMinions;
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

	public GameAction getAutoHeroPowerAction() {
		return getLogic().getAutoHeroPowerAction(getActivePlayerId());
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

	public Card getCardById(String cardId) {
		Card card = CardCatalogue.getCardById(cardId);
		if (card == null) {
			for (Card tempCard : getTempCards()) {
				if (tempCard.getCardId().equalsIgnoreCase(cardId)) {
					return tempCard.clone();
				}
			}
		}
		return card;
	}

	public List<CardCostModifier> getCardCostModifiers() {
		return cardCostModifiers;
	}

	@SuppressWarnings("unchecked")
	public Stack<Integer> getDamageStack() {
		if (!getEnvironment().containsKey(Environment.DAMAGE_STACK)) {
			getEnvironment().put(Environment.DAMAGE_STACK, new Stack<Integer>());
		}
		return (Stack<Integer>) getEnvironment().get(Environment.DAMAGE_STACK);
	}

	public DeckFormat getDeckFormat() {
		return deckFormat;
	}

	public HashMap<Environment, Object> getEnvironment() {
		return environment;
	}

	public Card getEventCard() {
		return (Card) resolveSingleTarget((EntityReference) getEnvironment().get(Environment.EVENT_CARD));
	}

	@SuppressWarnings("unchecked")
	public Stack<EntityReference> getEventTargetStack() {
		if (!getEnvironment().containsKey(Environment.EVENT_TARGET_REFERENCE_STACK)) {
			getEnvironment().put(Environment.EVENT_TARGET_REFERENCE_STACK, new Stack<EntityReference>());
		}
		return (Stack<EntityReference>) getEnvironment().get(Environment.EVENT_TARGET_REFERENCE_STACK);
	}

	public List<Actor> getLeftMinions(Player player, EntityReference minionReference) {
		List<Actor> leftMinions = new ArrayList<>();
		Actor minion = (Actor) resolveSingleTarget(minionReference);
		List<Minion> minions = getPlayer(minion.getOwner()).getMinions();
		int index = minions.indexOf(minion);
		if (index == -1) {
			return leftMinions;
		}
		for (int i = 0; i < index; i++) {
			leftMinions.add(minions.get(i));
		}
		return leftMinions;
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

	public List<Actor> getOppositeMinions(Player player, EntityReference minionReference) {
		List<Actor> oppositeMinions = new ArrayList<>();
		Actor minion = (Actor) resolveSingleTarget(minionReference);
		Player owner = getPlayer(minion.getOwner());
		Player opposingPlayer = getOpponent(owner);
		int index = owner.getMinions().indexOf(minion);
		if (opposingPlayer.getMinions().size() == 0 || owner.getMinions().size() == 0 || index == -1) {
			return oppositeMinions;
		}
		List<Minion> opposingMinions = opposingPlayer.getMinions();
		int delta = opposingPlayer.getMinions().size() - owner.getMinions().size();
		if (delta % 2 == 0) {
			delta /= 2;
			int epsilon = delta + index;
			if (epsilon > -1 && epsilon < opposingMinions.size()) {
				oppositeMinions.add(opposingMinions.get(epsilon));
			}
		} else {
			delta = (delta - 1) / 2;
			int epsilon = delta + index;
			if (epsilon > -1 && epsilon < opposingMinions.size()) {
				oppositeMinions.add(opposingMinions.get(epsilon));
			}
			if (epsilon + 1 > -1 && epsilon + 1 < opposingMinions.size()) {
				oppositeMinions.add(opposingMinions.get(epsilon + 1));
			}
		}
		return oppositeMinions;
	}

	public Card getPendingCard() {
		return (Card) resolveSingleTarget((EntityReference) getEnvironment().get(Environment.PENDING_CARD));
	}

	public synchronized Player getPlayer(int index) {
		return getPlayers().get(index);
	}

	public synchronized boolean hasPlayer(int id) {
		return id >= 0 && players != null && players.length > id && players[id] != null;
	}

	public Player getPlayer1() {
		return getPlayer(PLAYER_1);
	}

	public Player getPlayer2() {
		return getPlayer(PLAYER_2);
	}

	public synchronized List<Player> getPlayers() {
		if (players == null) {
			return Collections.unmodifiableList(new ArrayList<>());
		}
		return Collections.unmodifiableList(Arrays.asList(players));
	}

	public List<Actor> getRightMinions(Player player, EntityReference minionReference) {
		List<Actor> rightMinions = new ArrayList<>();
		Actor minion = (Actor) resolveSingleTarget(minionReference);
		List<Minion> minions = getPlayer(minion.getOwner()).getMinions();
		int index = minions.indexOf(minion);
		if (index == -1) {
			return rightMinions;
		}
		for (int i = index + 1; i < player.getMinions().size(); i++) {
			rightMinions.add(minions.get(i));
		}
		return rightMinions;
	}

	@SuppressWarnings("unchecked")
	public Stack<EntityReference> getSummonReferenceStack() {
		if (!getEnvironment().containsKey(Environment.SUMMON_REFERENCE_STACK)) {
			getEnvironment().put(Environment.SUMMON_REFERENCE_STACK, new Stack<EntityReference>());
		}
		return (Stack<EntityReference>) getEnvironment().get(Environment.SUMMON_REFERENCE_STACK);
	}

	public int getTotalMinionCount() {
		int totalMinionCount = 0;
		for (Player player : getPlayers()) {
			totalMinionCount += getMinionCount(player);
		}
		return totalMinionCount;
	}

	public List<IGameEventListener> getTriggersAssociatedWith(EntityReference entityReference) {
		return getTriggerManager().getTriggersAssociatedWith(entityReference);
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
		return getLogic().getValidActions(getActivePlayerId());
	}

	public int getWinningPlayerId() {
		return getWinner() == null ? -1 : getWinner().getId();
	}

	public boolean hasAutoHeroPower() {
		if (gameDecided()) {
			return false;
		}
		return getLogic().hasAutoHeroPower(getActivePlayerId());
	}

	public boolean ignoreEvents() {
		return ignoreEvents;
	}

	public void init() {
		int startingPlayerId = getLogic().determineBeginner(PLAYER_1, PLAYER_2);
		setActivePlayerId(getPlayer(startingPlayerId).getId());
		logger.debug(getActivePlayer().getName() + " begins");
		getLogic().init(getActivePlayerId(), true);
		getLogic().init(getOpponent(getActivePlayer()).getId(), false);
	}

	protected void onGameStateChanged() {
	}

	@Suspendable
	protected void performAction(int playerId, GameAction gameAction) {
		getLogic().performGameAction(playerId, gameAction);
		onGameStateChanged();
	}

	public void play() {
		logger.debug("Game starts: " + getPlayer1().getName() + " VS. " + getPlayer2().getName());
		init();
		while (!gameDecided()) {
			startTurn(getActivePlayerId());
			while (playTurn()) {
			}
			if (getTurn() > GameLogic.TURN_LIMIT) {
				break;
			}
		}
		endGame();
	}

	public boolean playTurn() {
		setActionsThisTurn(getActionsThisTurn() + 1);
		if (getActionsThisTurn() > 99) {
			logger.warn("Turn has been forcefully ended after {} actions", getActionsThisTurn());
			endTurn();
			return false;
		}
		if (getLogic().hasAutoHeroPower(getActivePlayerId())) {
			performAction(getActivePlayerId(), getAutoHeroPowerAction());
			return true;
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
		performAction(getActivePlayerId(), nextAction);

		return nextAction.getActionType() != ActionType.END_TURN;
	}

	public void printCurrentTriggers() {
		logger.info("Active spelltriggers:");
		getTriggerManager().printCurrentTriggers();
	}

	public void removeTrigger(IGameEventListener trigger) {
		getTriggerManager().removeTrigger(trigger);
	}

	public void removeTriggersAssociatedWith(EntityReference entityReference) {
		getTriggerManager().removeTriggersAssociatedWith(entityReference);
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

	public void setEventCard(Card eventCard) {
		if (eventCard != null) {
			getEnvironment().put(Environment.EVENT_CARD, eventCard.getReference());
		} else {
			getEnvironment().put(Environment.EVENT_CARD, null);
		}
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
		setTurn(getTurn() + 1);
		getLogic().startTurn(playerId);
		onGameStateChanged();
		setActionsThisTurn(0);
		setTurnState(TurnState.TURN_IN_PROGRESS);
	}

	@Override
	public String toString() {
		return String.format("[GameContext turn=%d turnState=%s]", getTurn(), getTurnState().toString());
	}

	public Entity tryFind(EntityReference targetKey) {
		try {
			return resolveSingleTarget(targetKey);
		} catch (Exception e) {
		}
		return null;
	}

	public void setLogic(GameLogic logic) {
		this.logic = logic;
		this.getLogic().setContext(this);
	}

	public void setDeckFormat(DeckFormat deckFormat) {
		this.deckFormat = deckFormat;
	}

	public void setEnvironment(HashMap<Environment, Object> environment) {
		this.environment = environment;
	}

	public void setCardCostModifiers(List<CardCostModifier> cardCostModifiers) {
		this.cardCostModifiers = cardCostModifiers;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public MatchResult getResult() {
		return result;
	}

	public void setResult(MatchResult result) {
		this.result = result;
	}

	public void setTurnState(TurnState turnState) {
		this.turnState = turnState;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getActionsThisTurn() {
		return actionsThisTurn;
	}

	public void setActionsThisTurn(int actionsThisTurn) {
		this.actionsThisTurn = actionsThisTurn;
	}

	public void addException(Throwable e) {
		exceptions.add(e);
	}

	public List<Throwable> getExceptions() {
		return Collections.unmodifiableList(exceptions);
	}

	public String toLongString() {
		StringBuilder builder = new StringBuilder("GameContext hashCode: " + hashCode() + "\nPlayer: ");
		for (Player player : getPlayers()) {
			if (player == null) {
				builder.append("(null player)\n");
				continue;
			}
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
		builder.append("Result: " + getResult() + "\n");
		builder.append("Winner: " + (getWinner() == null ? "tbd" : getWinner().getName()));

		builder.append("\nExceptions: \n");
		getExceptions().forEach(t -> {
			builder.append(t.getMessage() + "\n");
			builder.append(ExceptionUtils.getStackTrace(t));
			builder.append("\n");
		});

		return builder.toString();
	}

	public void setPlayer1(Player player1) {
		setPlayer(PLAYER_1, player1);
	}

	public void setPlayer2(Player player2) {
		setPlayer(PLAYER_2, player2);
	}

	public void loadState(GameState state) {
		this.setPlayer(GameContext.PLAYER_1, state.player1);
		this.setPlayer(GameContext.PLAYER_2, state.player2);
		this.setTempCards(state.tempCards);
		this.setCardCostModifiers(state.cardCostModifiers);
		this.setEnvironment(state.environment);
		this.setTriggerManager(state.triggerManager);
		this.getLogic().setIdFactory(new IdFactory(state.currentId));
		this.setTurnState(state.turnState);
	}

	public void setPlayer(int index, Player player) {
		this.players[index] = player;
	}

	public void setActivePlayerId(int id) {
		activePlayerId = id;
	}

	public TargetLogic getTargetLogic() {
		return targetLogic;
	}

	public void setTargetLogic(TargetLogic targetLogic) {
		this.targetLogic = targetLogic;
	}

	public TriggerManager getTriggerManager() {
		return triggerManager;
	}

	public void setTriggerManager(TriggerManager triggerManager) {
		this.triggerManager = triggerManager;
	}

	public CardCollection getTempCards() {
		return tempCards;
	}

	public void setTempCards(CardCollection tempCards) {
		this.tempCards = tempCards;
	}

	public boolean isDisposed() {
		return disposed;
	}
}
