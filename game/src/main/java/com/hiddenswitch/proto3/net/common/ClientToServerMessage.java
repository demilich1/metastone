package com.hiddenswitch.proto3.net.common;

import java.io.Serializable;
import java.util.List;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ClientToServerMessage implements Serializable {
	private MessageType mt;
	private Player player1;
	private Player callingPlayer;
	private GameAction action;
	private String gameId;
	private List<Card> discardedCards;
	private String id;

	public ClientToServerMessage(String id, GameAction action) {
		this.id = id;
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

	public ClientToServerMessage(String id, Player player, List<Card> discardedCards) {
		this.id = id;
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

	@Override
	public String toString() {
		Player player = getCallingPlayer() == null ? getPlayer1() : null;

		return new ToStringBuilder(this)
				.append("id", getId())
				.append("type", getMt())
				.append("gameId", getGameId())
				.append("action", getAction())
				.append("playerId", player == null ? 0 : player.getId())
				.toString();
	}

	public String getId() {
		return id;
	}
}
