package com.hiddenswitch.proto3.net.common;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;

import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.logic.GameLogic;

public class ServerGameContext extends GameContext {
	private final String gameId;
	private Map<Player, RemoteUpdateListener> listenerMap = new HashMap<>();
	private Consumer<List<Card>> mulliganCallback = null;
	private volatile Player player1;
	private volatile Player player2;
	private final WeakReference<Lock> lock;
	private volatile GameAction pendingAction = null;
	private volatile boolean actionRequested = false;


	public ServerGameContext(Player player1, Player player2, GameLogic logic, DeckFormat deckFormat, Lock lock, String gameId) {
		super(player1, player2, logic, deckFormat);
		NotificationProxy.init(new NullNotifier());
		this.player1 = player1;
		this.player2 = player2;
		this.lock = new WeakReference<>(lock);
		this.gameId = gameId;
	}

	public void setUpdateListener(Player player, RemoteUpdateListener listener) {
		listenerMap.put(player, listener);
	}

	public void updateAction(Player player, GameAction action) {
		if (actionRequested && player.getId() == getActivePlayer().getId()) {
			setPendingAction(action);
			actionRequested = false;
		} else {
			logger.error("Unexpected action received {} from player {}", action, player);
		}
	}

	@Override
	public void init() {
		int startingPlayerId = getLogic().determineBeginner(PLAYER_1, PLAYER_2);
		activePlayer = getPlayer(startingPlayerId).getId();
		logger.debug(getActivePlayer().getName() + " begins");

		getLock().lock();
		getListenerMap().get(getPlayer1()).setPlayers(getPlayer1(), getPlayer2());
		getListenerMap().get(getPlayer2()).setPlayers(getPlayer2(), getPlayer1());
		getListenerMap().get(getActivePlayer()).onActivePlayer(getActivePlayer());
		getListenerMap().get(getNonActivePlayer()).onActivePlayer(getActivePlayer());
		getLogic().init(activePlayer, true);
		getLogic().init(getOpponent(getActivePlayer()).getId(), false);
		getLock().unlock();
	}

	@Override
	protected void startTurn(int playerId) {
		super.startTurn(playerId);
		getListenerMap().get(getPlayer1()).onUpdate(getPlayer(0), getPlayer(1), TurnState.TURN_IN_PROGRESS);
		getListenerMap().get(getPlayer2()).onUpdate(getPlayer(0), getPlayer(1), TurnState.TURN_IN_PROGRESS);
	}

	public void endTurn() {
		logger.debug("Ending turn: " + getActivePlayer().getId());
		setPendingAction(null);
		actionRequested = false;
		super.endTurn();
		this.onGameStateChanged();
		logger.debug("Active player changed to: " + getActivePlayer().getId());
		getListenerMap().get(getPlayer1()).onTurnEnd(getActivePlayer(), getTurn(), getTurnState());
		getListenerMap().get(getPlayer2()).onTurnEnd(getActivePlayer(), getTurn(), getTurnState());
	}

	public Player getNonActivePlayer() {
		return getOpponent(getActivePlayer());
	}

	@Override
	public void play() {
		logger.debug("Game starts: " + getPlayer1().getName() + " VS. " + getPlayer2().getName());
		init();
		while (!gameDecided()) {
			getLock().lock();
			startTurn(activePlayer);
			while (playTurn()) {
				getLock().unlock();
				getLock().lock();
			}
			getLock().unlock();
			if (getTurn() > GameLogic.TURN_LIMIT) {
				break;
			}
		}
		getLock().lock();
		endGame();
		getLock().unlock();
	}


	@Override
	protected void onGameStateChanged() {
		getListenerMap().get(getPlayer1()).onUpdate(getPlayer(0), getPlayer(1), getTurnState());
		getListenerMap().get(getPlayer2()).onUpdate(getPlayer(0), getPlayer(1), getTurnState());
	}

	@Override
	public Player getPlayer1() {
		return player1;
	}

	@Override
	public Player getPlayer2() {
		return player2;
	}

	@Override
	public void fireGameEvent(GameEvent gameEvent) {
		super.fireGameEvent(gameEvent);
		getListenerMap().get(getPlayer1()).onGameEvent(gameEvent);
		getListenerMap().get(getPlayer2()).onGameEvent(gameEvent);

	}

	public void requestAction(Player player, List<GameAction> actions) {
		this.setPendingAction(null);
		actionRequested = true;
		getListenerMap().get(player).onRequestAction(actions);
	}

	private Lock getLock() {
		return lock.get();
	}

	public void mulligan(Player player, List<Card> starterCards, Consumer<List<Card>> callBack) {
		if (mulliganCallback != null) {
			logger.debug("Many mulligans at once is unsupported. Called by {}", player);
		}
		mulliganCallback = callBack;
		getListenerMap().get(player).onMulligan(player, starterCards);
	}

	public void sendGameOver(Player sender, Player winner) {
		getListenerMap().get(sender).onGameEnd(winner);
	}

	public void cycleLock() {
		getLock().unlock();
		getLock().lock();
	}

	public void updateMulligan(Player player, List<Card> discardedCards) {
		logger.debug(String.format("Mulligan received from %s", player.getName()));
		mulliganCallback.accept(discardedCards);
		mulliganCallback = null;
	}

	@Override
	public String toString() {
		return String.format("[ServerGameContext gameId=%s turn=%d]", getGameId(), getTurn());
	}

	public String getGameId() {
		return gameId;
	}

	public GameAction getPendingAction() {
		return pendingAction;
	}

	public void setPendingAction(GameAction pendingAction) {
		this.pendingAction = pendingAction;
	}

	public Map<Player, RemoteUpdateListener> getListenerMap() {
		return Collections.unmodifiableMap(listenerMap);
	}
}
