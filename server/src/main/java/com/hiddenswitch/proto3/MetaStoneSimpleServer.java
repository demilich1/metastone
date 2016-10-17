package com.hiddenswitch.proto3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.logic.ProceduralGameLogic;

public class MetaStoneSimpleServer {
	public static Player player1;
	public static Player player2;

	public static Lock simpleLock = new ReentrantLock();

	public static void main(String[] args) {

		SocketServerCommunication ssc = new SocketServerCommunication();

		ServerCommunicationSend scs = ssc;
		ServerCommunicationReceive scr = ssc;
		new Thread(ssc).start();
		System.out.println("working");
		scr.registerListener(new ServerListener() {
			ServerGameContext gameContext;

			@Override
			public void onPlayerConnected(Player p1, Player p2) {
				if (player1 == null) {
					player1 = p1;
					return;
				} else if (player2 == null) {
					player2 = p2;
					DeckFormat simpleFormat = new DeckFormat();
					simpleFormat.addSet(CardSet.PROCEDURAL_PREVIEW);
					gameContext = new ServerGameContext(player1, player2, new ProceduralGameLogic(), simpleFormat);
					gameContext.setUpdateListener(player1, ssc.getPlayerListener(0));
					gameContext.setUpdateListener(player2, ssc.getPlayerListener(1));
					new Thread(new Runnable() {
						@Override
						public void run() {
							gameContext.play();
						}
					}).start();
				} else {
					//TODO : Add support for many, many players
					throw new RuntimeException("not yet supported");
				}
			}

			@Override
			public void onActionRegistered(Player callingPlayer, GameAction action) {
				if (gameContext != null) {
					gameContext.updateAction(callingPlayer, action);
				}
			}
		});
	}
}
