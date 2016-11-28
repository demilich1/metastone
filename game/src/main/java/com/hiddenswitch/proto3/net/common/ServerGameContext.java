package com.hiddenswitch.proto3.net.common;

import co.paralleluniverse.fibers.Suspendable;
import io.vertx.core.Handler;
import io.vertx.core.WorkerExecutor;
import io.vertx.ext.sync.Sync;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.logic.GameLogic;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ServerGameContext extends GameContext {
	private final String gameId;
	private Map<Player, RemoteUpdateListener> listenerMap = new HashMap<>();
	private final Map<String, Handler> requestCallbacks = new HashMap<>();

	public ServerGameContext(Player player1, Player player2, DeckFormat deckFormat, String gameId) {
		// The player's IDs are set here
		super(player1, player2, new GameLogicAsync(), deckFormat);
		if (player1.getId() == player2.getId()) {
			throw new RuntimeException("The player IDs in this ServerGameContext are equal, which is not allowed.");
		}
		NotificationProxy.init(new NullNotifier());
		this.gameId = gameId;
	}

	public GameLogicAsync getNetworkGameLogic() {
		return (GameLogicAsync) getLogic();
	}

	public void setUpdateListener(Player player, RemoteUpdateListener listener) {
		listenerMap.put(player, listener);
	}

	@Override
	public void init() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("ServerGameContext::init is unsupported.");
	}

	@Override
	protected void startTurn(int playerId) {
		super.startTurn(playerId);
		getListenerMap().get(getPlayer1()).onUpdate(getPlayer(0), getPlayer(1), TurnState.TURN_IN_PROGRESS);
		getListenerMap().get(getPlayer2()).onUpdate(getPlayer(0), getPlayer(1), TurnState.TURN_IN_PROGRESS);
	}

	public void endTurn() {
		logger.debug("Ending turn: " + getActivePlayer().getId());
		super.endTurn();
		this.onGameStateChanged();
		logger.debug("Active player changed to: " + getActivePlayer().getId());
		getListenerMap().get(getPlayer1()).onTurnEnd(getActivePlayer(), getTurn(), getTurnState());
		getListenerMap().get(getPlayer2()).onTurnEnd(getActivePlayer(), getTurn(), getTurnState());
	}

	private Player getNonActivePlayer() {
		return getOpponent(getActivePlayer());
	}

	@Override
	public void play() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("ServerGameContext::play should not be called. Use ::networkPlay instead.");
	}

	public void networkPlay() {
		logger.debug("Game starts: " + getPlayer1().getName() + " VS. " + getPlayer2().getName());
		int startingPlayerId = getLogic().determineBeginner(PLAYER_1, PLAYER_2);
		this.activePlayer = getPlayer(startingPlayerId).getId();
		logger.debug(getActivePlayer().getName() + " begins");

		getListenerMap().get(getPlayer1()).setPlayers(getPlayer1(), getPlayer2());
		getListenerMap().get(getPlayer2()).setPlayers(getPlayer2(), getPlayer1());
		getListenerMap().get(getActivePlayer()).onActivePlayer(getActivePlayer());
		getListenerMap().get(getNonActivePlayer()).onActivePlayer(getActivePlayer());

		getNetworkGameLogic().initAsync(this.activePlayer, true, _ap -> {
			getNetworkGameLogic().initAsync(getOpponent(getActivePlayer()).getId(), false, _op -> {
				Recursive<Runnable> playTurnLoop = new Recursive<>();
				playTurnLoop.func = () -> {
					// Check if the game has been decided right at the end of the player's turn
					if (gameDecided()) {
						endGame();
						return;
					}

					startTurn(getActivePlayerId());
					Recursive<Handler<Boolean>> actionLoop = new Recursive<>();

					actionLoop.func = hasMoreActions -> {
						if (hasMoreActions) {
							networkedPlayTurn(actionLoop.func);
						} else {
							if (getTurn() > GameLogic.TURN_LIMIT
									|| gameDecided()) {
								endGame();
							} else {
								playTurnLoop.func.run();
							}
						}
					};

					networkedPlayTurn(actionLoop.func);
				};

				// Start the active player's turn once the game is initialized.
				playTurnLoop.func.run();
			});
		});
	}

	@Override
	public boolean playTurn() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("ServerGameContext::playTurn should not be called.");
	}

	@Suspendable
	protected void networkedPlayTurn(Handler<Boolean> callback) {
		setActionsThisTurn(getActionsThisTurn() + 1);

		if (getActionsThisTurn() > 99) {
			logger.warn("Turn has been forcefully ended after {} actions", getActionsThisTurn());
			endTurn();
			callback.handle(false);
			return;
		}

		if (getLogic().hasAutoHeroPower(activePlayer)) {
			performAction(activePlayer, getAutoHeroPowerAction());
			callback.handle(true);
			return;
		}

		List<GameAction> validActions = getValidActions();
		if (validActions.size() == 0) {
			endTurn();
			callback.handle(false);
			return;
		}

		NetworkBehaviour networkBehaviour = (NetworkBehaviour) getActivePlayer().getBehaviour();
		networkBehaviour.requestActionAsync(this, getActivePlayer(), getValidActions(), action -> {
			if (action == null) {
				throw new RuntimeException("Behaviour " + getActivePlayer().getBehaviour().getName() + " selected NULL action while "
						+ getValidActions().size() + " actions were available");
			}
			performAction(getActivePlayerId(), action);
			callback.handle(action.getActionType() != ActionType.END_TURN);
		});
	}

	@Override
	protected void onGameStateChanged() {
		getListenerMap().get(getPlayer1()).onUpdate(getPlayer(0), getPlayer(1), getTurnState());
		getListenerMap().get(getPlayer2()).onUpdate(getPlayer(0), getPlayer(1), getTurnState());
	}

	@Override
	public void fireGameEvent(GameEvent gameEvent) {
		super.fireGameEvent(gameEvent);
		getListenerMap().get(getPlayer1()).onGameEvent(gameEvent);
		getListenerMap().get(getPlayer2()).onGameEvent(gameEvent);

	}

	@Suspendable
	public void networkRequestAction(Player player, List<GameAction> actions, Handler<GameAction> callback) {
		logger.debug("Requesting acton for playerId {} hashCode {}", player.getId(), player.hashCode());
		String id = RandomStringUtils.randomAscii(8);
		requestCallbacks.put(id, callback);
		getListenerMap().get(player).onRequestAction(id, actions);
	}

	public void networkRequestMulligan(Player player, List<Card> starterCards, Handler<List<Card>> callback) {
		logger.debug("Requesting mulligan for playerId {} hashCode {}", player.getId(), player.hashCode());
		String id = RandomStringUtils.randomAscii(8);
		requestCallbacks.put(id, callback);
		getListenerMap().get(player).onMulligan(id, player, starterCards);
	}

	@Suspendable
	@SuppressWarnings("unchecked")
	public void onActionReceived(String messageId, Player player, GameAction action) {
		logger.debug("Accepting acton for playerId {} hashCode {}", player.getId(), player.hashCode());
		Sync.fiberHandler((Handler<GameAction>) requestCallbacks.get(messageId)).handle(action);
		requestCallbacks.remove(messageId);
	}

	@SuppressWarnings("unchecked")
	public void onMulliganReceived(String messageId, Player player, List<Card> discardedCards) {
		logger.debug("Mulligan received from {}", player.getName());
		((Handler<List<Card>>) requestCallbacks.get(messageId)).handle(discardedCards);
		requestCallbacks.remove(messageId);
	}

	public void sendGameOver(Player sender, Player winner) {
		getListenerMap().get(sender).onGameEnd(winner);
	}

	@Override
	protected void notifyPlayersGameOver() {
		for (Player player : getPlayers()) {
			NetworkBehaviour networkBehaviour = (NetworkBehaviour) player.getBehaviour();
			networkBehaviour.onGameOverAsync(this, player.getId(), getWinner() != null ? getWinner().getId() : -1);
		}
	}

	@Override
	public String toString() {
		return String.format("[ServerGameContext gameId=%s turn=%d]", getGameId(), getTurn());
	}

	public String getGameId() {
		return gameId;
	}

	public Map<Player, RemoteUpdateListener> getListenerMap() {
		return Collections.unmodifiableMap(listenerMap);
	}
}
