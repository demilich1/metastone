package com.hiddenswitch.proto3.net.common;

import java.io.Serializable;
import java.util.List;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;

public class ClientToServerMessage implements Serializable {
	private MessageType mt;
	private Player player1;
	private Player callingPlayer;
	private GameAction action;
	private String gameId;
	private List<Card> discardedCards;

	public ClientToServerMessage(Player player, GameAction action) {
		this.setCallingPlayer(player);
		this.setAction(action);
		setMt(MessageType.UPDATE_ACTION);
	}

	public ClientToServerMessage(Player p1) {
		this.setPlayer1(p1);
		setMt(MessageType.REGISTER_PLAYER);
	}

	public ClientToServerMessage(Player player, String gameId) {
		this.setPlayer1(player);
		this.setGameId(gameId);
		setMt(MessageType.FIRST_MESSAGE);
	}

	public ClientToServerMessage(Player player, List<Card> discardedCards) {
		this.setPlayer1(player);
		this.discardedCards = discardedCards;
		setMt(MessageType.UPDATE_MULLIGAN);
	}

	public MessageType getMt() {
		return mt;
	}

	public void setMt(MessageType mt) {
		this.mt = mt;
	}

	public void setGameId(String id) {
		this.gameId = id;
	}

	public String getGameId() {
		return gameId;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
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

	public List<Card> getDiscardedCards() {
		// TODO Auto-generated method stub
		return discardedCards;
	}


}
