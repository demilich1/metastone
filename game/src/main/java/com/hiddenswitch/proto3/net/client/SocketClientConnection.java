package com.hiddenswitch.proto3.net.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.hiddenswitch.proto3.net.common.ClientToServerMessage;
import com.hiddenswitch.proto3.net.common.RemoteUpdateListener;
import com.hiddenswitch.proto3.net.common.ServerToClientMessage;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;

public class SocketClientConnection implements ClientCommunicationReceive, ClientCommunicationSend, Runnable {
	private final String host;
	private final int port;
	private BlockingQueue<ClientToServerMessage> queue = new LinkedBlockingQueue<>();
	private RemoteUpdateListener remoteUpdateListener;
	private boolean shouldRun = true;

	public SocketClientConnection() {
		this("127.0.0.1", 11111);
	}

	public SocketClientConnection(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public SendToServer getSendToServer() {
		return new SendToServer() {

			@Override
			public void registerAction(Player callingPlayer, GameAction action) {
				queue.add(new ClientToServerMessage(callingPlayer, action));
			}

			@Override
			public void sendMulligan(Player player, List<Card> discardedCards) {
				queue.add(new ClientToServerMessage(player, discardedCards));

			}

			@Override
			public void sendGenericMessage(ClientToServerMessage message) {
				queue.add(message);
			}
		};
	}

	@Override
	public void RegisterListener(RemoteUpdateListener remoteUpdateListener) {
		this.remoteUpdateListener = remoteUpdateListener;
	}

	@Override
	public void run() {
		try {
			Socket socket = new Socket(getHost(), getPort());
			//ReadThread
			new Thread(() -> {
				try {
					ObjectInputStream serverInputStream = new ObjectInputStream(socket.getInputStream());
					while (shouldRun) {
						ServerToClientMessage message = (ServerToClientMessage) serverInputStream.readObject();
						switch (message.mt) {
							case ON_GAME_EVENT:
								remoteUpdateListener.onGameEvent(message.event);
								break;
							case ON_GAME_END:
								remoteUpdateListener.onGameEnd(message.winner);
								break;
							case SET_PLAYERS:
								remoteUpdateListener.setPlayers(message.localPlayer, message.remotePlayer);
								break;
							case ON_ACTIVE_PLAYER:
								remoteUpdateListener.onActivePlayer(message.activePlayer);
								break;
							case ON_UPDATE:
								remoteUpdateListener.onUpdate(message.player1, message.player2, message.turnState);
								break;
							case ON_TURN_END:
								remoteUpdateListener.onTurnEnd(message.activePlayer, message.turnNumber, message.turnState);
								break;
							case ON_REQUEST_ACTION:
								remoteUpdateListener.onRequestAction(message.actions);
								break;
							case ON_MULLIGAN:
								remoteUpdateListener.onMulligan(message.player1, message.startingCards);
								break;
							default:
								System.err.println("Unexpected message from server received");
								break;
						}
					}
					serverInputStream.close();
					socket.close();
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}).start();

			//Write Thread
			new Thread(() -> {
				try {
					ObjectOutputStream serverOutputStream = new ObjectOutputStream(socket.getOutputStream());
					while (shouldRun) {
						ClientToServerMessage message = queue.take();
						serverOutputStream.writeObject(message);
						serverOutputStream.flush();
						serverOutputStream.reset();
					}
					serverOutputStream.close();
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}

			}).start();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void kill() {
		shouldRun = false;
	}


	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}
}
