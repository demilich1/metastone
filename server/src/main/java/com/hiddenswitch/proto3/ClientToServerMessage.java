package com.hiddenswitch.proto3;

import java.io.Serializable;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;

public class ClientToServerMessage implements Serializable {
	private MessageType mt;
	private Player player1;
	private Player player2;
	private Player callingPlayer;
	private GameAction action;

	public ClientToServerMessage(Player player, GameAction action) {
		this.setCallingPlayer(player);
		this.setAction(action);
		setMt(MessageType.updateAction);
	}

	public ClientToServerMessage(Player p1, Player p2) {
		this.setPlayer1(p1);
		this.setPlayer2(p2);
		setMt(MessageType.registerPlayer);
	}

	public MessageType getMt() {
		return mt;
	}

	public void setMt(MessageType mt) {
		this.mt = mt;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public Player getCallingPlayer() {
		return callingPlayer;
	}

	public void setCallingPlayer(Player callingPlayer) {
		this.callingPlayer = callingPlayer;
	}

	public GameAction getAction() {
		return action;
	}

	public void setAction(GameAction action) {
		this.action = action;
	}
}
