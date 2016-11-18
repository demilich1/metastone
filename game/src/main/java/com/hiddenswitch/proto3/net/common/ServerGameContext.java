package com.hiddenswitch.proto3.net.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

	public Map<Player, RemoteUpdateListener> listenerMap = new HashMap<>();
	private Consumer<List<Card>> mulliganCallback = null;
	private volatile Player player1;
	private volatile Player player2;
	private final Lock lock;


	public ServerGameContext(Player player1, Player player2, GameLogic logic, DeckFormat deckFormat, Lock lock) {
		super(player1, player2, logic, deckFormat);
		NotificationProxy.init(new NullNotifier());
		this.player1 = player1;
		this.player2 = player2;
		this.lock = lock;
	}

	public void setUpdateListener(Player player, RemoteUpdateListener listener) {
		listenerMap.put(player, listener);
	}

	public void updateAction(Player player, GameAction action) {
		if (actionRequested && player.getId() == getActivePlayer().getId()) {
			pendingAction = action;
			actionRequested = false;
		} else {
			System.err.println("unexpected action received");
		}
	}


	public volatile GameAction pendingAction = null;
	volatile boolean actionRequested = false;

	@Override
	public void init() {
		int startingPlayerId = getLogic().determineBeginner(PLAYER_1, PLAYER_2);
		activePlayer = getPlayer(startingPlayerId).getId();
		logger.debug(getActivePlayer().getName() + " begins");

		getLock().lock();
		listenerMap.get(getPlayer1()).setPlayers(getPlayer1(), getPlayer2());
		listenerMap.get(getPlayer2()).setPlayers(getPlayer2(), getPlayer1());
		listenerMap.get(getActivePlayer()).onActivePlayer(getActivePlayer());
		listenerMap.get(getNonActivePlayer()).onActivePlayer(getActivePlayer());
		getLogic().init(activePlayer, true);
		getLogic().init(getOpponent(getActivePlayer()).getId(), false);
		getLock().unlock();
	}

	@Override
	protected void startTurn(int playerId) {
		super.startTurn(playerId);
		listenerMap.get(getPlayer1()).onUpdate(getPlayer(0), getPlayer(1), TurnState.TURN_IN_PROGRESS);
		listenerMap.get(getPlayer2()).onUpdate(getPlayer(0), getPlayer(1), TurnState.TURN_IN_PROGRESS);
	}

	public void endTurn() {
		System.out.println("Ending turn: " + getActivePlayer().getId());
		pendingAction = null;
		actionRequested = false;
		super.endTurn();
		this.onGameStateChanged();
		System.out.println("now the active player is: " + getActivePlayer().getId());
		listenerMap.get(getPlayer1()).onTurnEnd(getActivePlayer(), getTurn(), getTurnState());
		listenerMap.get(getPlayer2()).onTurnEnd(getActivePlayer(), getTurn(), getTurnState());
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
		listenerMap.get(getPlayer1()).onUpdate(getPlayer(0), getPlayer(1), getTurnState());
		listenerMap.get(getPlayer2()).onUpdate(getPlayer(0), getPlayer(1), getTurnState());
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
		listenerMap.get(getPlayer1()).onGameEvent(gameEvent);
		listenerMap.get(getPlayer2()).onGameEvent(gameEvent);

	}

	public void requestAction(Player player, List<GameAction> actions) {
		this.pendingAction = null;
		actionRequested = true;
		listenerMap.get(player).onRequestAction(actions);
	}

	private Lock getLock() {
		return lock;
	}

	public void mulligan(Player player, List<Card> starterCards, Consumer<List<Card>> callBack) {
		if (mulliganCallback != null){
			System.err.print("many mulligans at once is unsupported.");
		}
		mulliganCallback = callBack;
		listenerMap.get(player).onMulligan(player, starterCards);
	}
	
	public void sendGameOver(Player sender, Player winner){
		listenerMap.get(sender).onGameEnd(winner);
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
}
