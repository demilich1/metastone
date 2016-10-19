package com.hiddenswitch.proto3.common;

import java.io.Serializable;
import java.util.List;

import com.hiddenswitch.proto3.common.MessageType;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.events.GameEvent;

public class ServerToClientMessage implements Serializable {
	public MessageType mt;
	public GameEvent event;
	public Player winner;
	public Player localPlayer, remotePlayer;
	public Player activePlayer;
	public int turnNumber;
	public TurnState turnState;
	public Player player1, player2;
	public List<GameAction> actions;


	public ServerToClientMessage(GameEvent event) {
		this.mt = MessageType.ON_GAME_EVENT;
		this.event = event;
	}

	public ServerToClientMessage(Player player, boolean isGameEnd) {
		if (isGameEnd) {
			this.winner = player;
			this.mt = MessageType.ON_GAME_END;
		} else {
			this.activePlayer = player;
			this.mt = MessageType.ON_ACTIVE_PLAYER;
		}

	}

	public ServerToClientMessage(Player localPlayer, Player remotePlayer) {
		this.localPlayer = localPlayer;
		this.remotePlayer = remotePlayer;
		this.mt = MessageType.SET_PLAYERS;
	}

	public ServerToClientMessage(Player player, int turn, TurnState newState) {
		this.activePlayer = player;
		this.turnNumber = turn;
		this.turnState = newState;
		this.mt = MessageType.ON_TURN_END;
	}

	public ServerToClientMessage(Player player, Player player2, TurnState newState) {
		this.player1 = player;
		this.player2 = player2;
		this.turnState = newState;
		this.mt = MessageType.ON_UPDATE;
	}

	public ServerToClientMessage(List<GameAction> actions) {
		this.actions = actions;
		this.mt = MessageType.ON_REQUEST_ACTION;
	}


}
