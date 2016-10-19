package com.hiddenswitch.proto3.server;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.logic.ProceduralGameLogic;

public class SocketServerListener implements ServerListener {
	private final SocketServerSession ssc;
	private ServerGameContext gameContext;
	private Player player1;
	private Player player2;

	public SocketServerListener(SocketServerSession ssc) {
		this.ssc = ssc;
	}

	@Override
	public void onPlayerConnected(Player p1, Player p2) {
		if (getPlayer1() == null) {
			setPlayer1(p1);
			return;
		} else if (getPlayer2() == null) {
			setPlayer2(p2);
			DeckFormat simpleFormat = new DeckFormat();
			simpleFormat.addSet(CardSet.PROCEDURAL_PREVIEW);
			setGameContext(new ServerGameContext(getPlayer1(), getPlayer2(), new ProceduralGameLogic(), simpleFormat));
			getGameContext().setUpdateListener(getPlayer1(), getSsc().getPlayerListener(0));
			getGameContext().setUpdateListener(getPlayer2(), getSsc().getPlayerListener(1));
			new Thread(() -> getGameContext().play()).start();
		} else {
			//TODO : Add support for many, many players
			throw new RuntimeException("not yet supported");
		}
	}

	@Override
	public void onActionRegistered(Player callingPlayer, GameAction action) {
		if (getGameContext() != null) {
			getGameContext().updateAction(callingPlayer, action);
		}
	}

	public SocketServerSession getSsc() {
		return ssc;
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
