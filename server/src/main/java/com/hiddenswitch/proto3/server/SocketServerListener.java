package com.hiddenswitch.proto3.server;

import java.util.concurrent.locks.Lock;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.logic.ProceduralGameLogic;

public class SocketServerListener implements ServerListener {
	private final ServerCommunicationSend serverCommunicationSend;
	private ServerGameContext gameContext;
	private Player player1;
	private Player player2;
	private Lock gameLock;

	public SocketServerListener(ServerCommunicationSend serverCommunicationSend, Lock gameLock) {
		this.serverCommunicationSend = serverCommunicationSend;
		this.gameLock = gameLock;
	}

	@Override
	public void onPlayerConnected(Player player) {
		if (getPlayer1() == null) {
			setPlayer1(player);
			return;
		} else if (getPlayer2() == null) {
			setPlayer2(player);
			DeckFormat simpleFormat = new DeckFormat();
			simpleFormat.addSet(CardSet.PROCEDURAL_PREVIEW);
			setGameContext(new ServerGameContext(getPlayer1(), getPlayer2(), new ProceduralGameLogic(), simpleFormat, gameLock));
			getGameContext().setUpdateListener(getPlayer1(), getSender().getPlayerListener(0));
			getGameContext().setUpdateListener(getPlayer2(), getSender().getPlayerListener(1));
			new Thread(() -> getGameContext().play()).start();
		} else {
			throw new RuntimeException("too many players connected!");
		}
	}

	@Override
	public void onActionRegistered(Player callingPlayer, GameAction action) {
		if (getGameContext() != null) {
			getGameContext().updateAction(callingPlayer, action);
		}
	}

	public ServerCommunicationSend getSender() {
		return serverCommunicationSend;
	}

	public ServerGameContext getGameContext() {
		return gameContext;
	}

	public void setGameContext(ServerGameContext gameContext) {
		this.gameContext = gameContext;
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
}
