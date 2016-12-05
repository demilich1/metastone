package com.hiddenswitch.proto3.net.common;

import co.paralleluniverse.fibers.Suspendable;
import io.vertx.core.Handler;
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
import net.demilich.metastone.game.targeting.IdFactory;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerGameContext extends GameContext {
	private final String gameId;
	private Map<Player, RemoteUpdateListener> listenerMap = new HashMap<>();
	private final Map<String, Handler> requestCallbacks = new HashMap<>();

	public ServerGameContext(Player player1, Player player2, DeckFormat deckFormat, String gameId) {
		// The player's IDs are set here
		super(player1, player2, new GameLogicAsync(), deckFormat);
		if (player1.getId() == player2.getId()
				|| player1.getId() == IdFactory.UNASSIGNED
				|| player2.getId() == IdFactory.UNASSIGNED) {
			player1.setId(IdFactory.PLAYER_1);
			player2.setId(IdFactory.PLAYER_2);
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
		GameState state = new GameState(this, TurnState.TURN_IN_PROGRESS);
		getListenerMap().get(getPlayer1()).onUpdate(state);
		getListenerMap().get(getPlayer2()).onUpdate(state);
	}

	public void endTurn() {
		logger.debug("Ending turn: " + getActivePlayer().getId());
		super.endTurn();
		this.onGameStateChanged();
		logger.debug("Active player changed to: " + getActivePlayerId());
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

	@Suspendable
	public void networkPlay() {
		logger.debug("Game starts: " + getPlayer1().getName() + " VS. " + getPlayer2().getName());
		int startingPlayerId = getLogic().determineBeginner(PLAYER_1, PLAYER_2);
		setActivePlayerId(getPlayer(startingPlayerId).getId());
		logger.debug(getActivePlayer().getName() + " begins");

		getListenerMap().get(getActivePlayer()).onActivePlayer(getActivePlayer());
		getListenerMap().get(getNonActivePlayer()).onActivePlayer(getActivePlayer());

		// Make sure the players are initialized before sending the original player updates.
		getNetworkGameLogic().initializePlayer(IdFactory.PLAYER_1);
		getNetworkGameLogic().initializePlayer(IdFactory.PLAYER_2);

		getListenerMap().get(getPlayer1()).setPlayers(getPlayer1(), getPlayer2());
		getListenerMap().get(getPlayer2()).setPlayers(getPlayer2(), getPlayer1());

		getNetworkGameLogic().initAsync(getActivePlayerId(), true, _ap -> {
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

		if (getLogic().hasAutoHeroPower(getActivePlayerId())) {
			performAction(getActivePlayerId(), getAutoHeroPowerAction());
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
		GameState state = new GameState(this);
		getListenerMap().get(getPlayer1()).onUpdate(state);
		getListenerMap().get(getPlayer2()).onUpdate(state);
	}

	@Override
	public void fireGameEvent(GameEvent gameEvent) {
		super.fireGameEvent(gameEvent);
		getListenerMap().get(getPlayer1()).onGameEvent(gameEvent);
		getListenerMap().get(getPlayer2()).onGameEvent(gameEvent);

	}

	@Suspendable
	public void networkRequestAction(GameState state, int playerId, List<GameAction> actions, Handler<GameAction> callback) {
		String id = RandomStringUtils.randomAscii(8);
		logger.debug("Requesting action with callback {} for playerId {}", id, playerId);
		requestCallbacks.put(id, callback);
		getListenerMap().get(getPlayer(playerId)).onRequestAction(id, state, actions);
	}

	public void networkRequestMulligan(Player player, List<Card> starterCards, Handler<List<Card>> callback) {
		logger.debug("Requesting mulligan for playerId {} hashCode {}", player.getId(), player.hashCode());
		String id = RandomStringUtils.randomAscii(8);
		requestCallbacks.put(id, callback);
		getListenerMap().get(player).onMulligan(id, player, starterCards);
	}

	@Suspendable
	@SuppressWarnings("unchecked")
	public void onActionReceived(String messageId, GameAction action) {
		logger.debug("Accepting action for callback {}", messageId);
		final Handler handler = requestCallbacks.get(messageId);
		requestCallbacks.remove(messageId);
		Sync.fiberHandler((Handler<GameAction>) handler).handle(action);
		logger.debug("Action executed for callback {}", messageId);
	}

	@SuppressWarnings("unchecked")
	public void onMulliganReceived(String messageId, Player player, List<Card> discardedCards) {
		logger.debug("Mulligan received from {}", player.getName());
		final Handler handler = requestCallbacks.get(messageId);
		requestCallbacks.remove(messageId);
		((Handler<List<Card>>) handler).handle(discardedCards);
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