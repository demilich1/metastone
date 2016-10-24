package com.hiddenswitch.proto3.net.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.hiddenswitch.proto3.net.common.ClientToServerMessage;
import com.hiddenswitch.proto3.net.common.RemoteUpdateListener;
import com.hiddenswitch.proto3.net.common.ServerToClientMessage;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;

public class SocketClientConnection implements ClientCommunicationReceive, ClientCommunicationSend, Runnable {
	private BlockingQueue<ClientToServerMessage> queue = new LinkedBlockingQueue<>();
	private RemoteUpdateListener remoteUpdateListener;
	private boolean shouldRun = true;

	@Override
	public SendToServer getSendToServer() {
		return new SendToServer() {
			@Override
			public void registerPlayer(Player player) {
				queue.add(new ClientToServerMessage(player));
			}

			@Override
			public void registerAction(Player callingPlayer, GameAction action) {
				queue.add(new ClientToServerMessage(callingPlayer, action));
			}

			@Override
			public void sendFirstMessage(Player player, String gameId) {
				queue.add(new ClientToServerMessage(player, gameId));
				
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
			Socket socket = new Socket("127.0.0.1", 11111);
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


}
