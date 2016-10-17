package com.hiddenswitch.proto3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.logic.GameLogic;

public class ServerGameContext extends GameContext {

	public Map<Player, RemoteUpdateListener> listenerMap = new HashMap<>();
	private volatile Player player1;
	private volatile Player player2;

	public ServerGameContext(Player player1, Player player2, GameLogic logic, DeckFormat deckFormat) {
		super(player1, player2, logic, deckFormat);
		NotificationProxy.init(new NullNotifier());
		this.player1 = player1;
		this.player2 = player2;
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


	GameAction pendingAction = null;
	volatile boolean actionRequested = false;

	@Override
	public void init() {
		super.init();
		listenerMap.get(getPlayer1()).setPlayers(getPlayer1(), getPlayer2());
		listenerMap.get(getPlayer2()).setPlayers(getPlayer2(), getPlayer1());
		listenerMap.get(getActivePlayer()).onActivePlayer(getActivePlayer());
		listenerMap.get(getNonActivePlayer()).onActivePlayer(getActivePlayer());
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

	@Override
	public boolean gameDecided() {
		boolean gameDecided = super.gameDecided();
		if (gameDecided) {
			listenerMap.get(getPlayer1()).onGameEnd(winner);
			listenerMap.get(getPlayer2()).onGameEnd(winner);
		}
		return gameDecided;
	}

	public Player getNonActivePlayer() {
		return getOpponent(getActivePlayer());
	}

	@Override
	public void play() {
		logger.debug("Game starts: " + getPlayer1().getName() + " VS. " + getPlayer2().getName());
		init();
		while (!gameDecided()) {
			MetaStoneSimpleServer.simpleLock.lock();
			startTurn(activePlayer);
			while (playTurn()) {
				MetaStoneSimpleServer.simpleLock.unlock();
				MetaStoneSimpleServer.simpleLock.lock();
			}
			MetaStoneSimpleServer.simpleLock.unlock();
			if (getTurn() > GameLogic.TURN_LIMIT) {
				break;
			}
		}
		MetaStoneSimpleServer.simpleLock.unlock();
		MetaStoneSimpleServer.simpleLock.lock();
		endGame();
		MetaStoneSimpleServer.simpleLock.unlock();
	}

	@Override
	public boolean playTurn() {
		if (actionRequested == true && pendingAction == null) {
			//busy wait
			return true;
		}
		setActionsThisTurn(getActionsThisTurn() + 1);
		if (getActionsThisTurn() > 99) {
			logger.warn("Turn has been forcefully ended after {} actions", getActionsThisTurn());
			endTurn();
			return false;
		}
		if (getLogic().hasAutoHeroPower(activePlayer)) {
			performAction(activePlayer, getAutoHeroPowerAction());
			return true;
		}

		List<GameAction> validActions = getValidActions();
		if (validActions.size() == 0) {
			endTurn();
			return false;
		}
		if (pendingAction == null && actionRequested == false) {
			listenerMap.get(getActivePlayer()).onRequestAction(validActions);
			System.out.println("requesting actions from: " + getActivePlayer().getId());
			actionRequested = true;
			return true;
		} else if (pendingAction == null) {
			//Wait for action from player;
			return true;
		}   // else if (!validActions.contains(pendingAction)){
		//TODO: Handle cheating players;
		//(Currently this is broken due to comparison is broken)
		//throw new RuntimeException("invalid action provided by player");
		//}
		boolean endTurn = pendingAction.getActionType() == ActionType.END_TURN;
		performAction(activePlayer, pendingAction);
		if (!endTurn) {
			pendingAction = null;
			actionRequested = false;
		}
		return !endTurn;
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
}
