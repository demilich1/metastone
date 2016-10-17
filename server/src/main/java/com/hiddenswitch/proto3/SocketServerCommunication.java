package com.hiddenswitch.proto3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.events.GameEvent;

public class SocketServerCommunication implements ServerCommunicationReceive, ServerCommunicationSend, Runnable {
	ClientRunnable c1;
	ClientRunnable c2;
	ServerListener listener;
	Socket socketConnection;
	boolean shouldRun = true;

	public SocketServerCommunication() {
	}

	@Override
	public RemoteUpdateListener getPlayerListener(int player) {
		//TODO: FIX player checking
		if (player == 0) {
			return new SocketRemoteUpdateListener(c1);
		} else {
			return new SocketRemoteUpdateListener(c2);
		}
	}

	@Override
	public void registerListener(ServerListener listener) {
		this.listener = listener;
	}

	public void run() {
		try {
			ServerSocket s = new ServerSocket(11111);
			Socket socket = s.accept();
			c1 = new ClientRunnable(socket);
			new Thread(c1).start();

			Socket socket2 = s.accept();
			c2 = new ClientRunnable(socket2);
			new Thread(c2).start();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void kill() {
		shouldRun = false;
	}

	class ClientRunnable implements Runnable {
		Socket privateSocket;
		public BlockingQueue<ServerToClientMessage> queue = new LinkedBlockingQueue<>();

		ClientRunnable(Socket socket) {
			this.privateSocket = socket;
		}

		@Override
		public void run() {
			System.out.println("waiting for response from clients");
			//Read thread
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						ObjectInputStream clientInputStream = new ObjectInputStream(privateSocket.getInputStream());
						while (shouldRun) {

							ClientToServerMessage message = (ClientToServerMessage) clientInputStream.readObject();
							System.out.println("message received");
							System.out.println(message.getMt().toString());
							if (message.getMt() == MessageType.registerPlayer) {
								listener.onPlayerConnected(message.getPlayer1(), message.getPlayer2());
							} else {
								listener.onActionRegistered(message.getCallingPlayer(), message.getAction());
								;
							}
						}
						clientInputStream.close();
						socketConnection.close();
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
			}).start();

			//Write thread
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						ObjectOutputStream clientOutputStream = new ObjectOutputStream(privateSocket.getOutputStream());
						while (shouldRun) {
							ServerToClientMessage message = queue.take();
							MetaStoneSimpleServer.simpleLock.lock();
							System.out.println("Sending message: " + message.mt);
							clientOutputStream.writeObject(message);
							MetaStoneSimpleServer.simpleLock.unlock();
							clientOutputStream.flush();
							//super important magic line below:
							clientOutputStream.reset();
						}
						clientOutputStream.close();
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
						MetaStoneSimpleServer.simpleLock.unlock();

					}

				}
			}).start();
		}
	}

	class SocketRemoteUpdateListener implements RemoteUpdateListener {

		ClientRunnable client;

		public SocketRemoteUpdateListener(ClientRunnable client) {
			this.client = client;
		}

		@Override
		public void onGameEvent(GameEvent event) {
			client.queue.add(new ServerToClientMessage(event));

		}

		@Override
		public void onGameEnd(Player winner) {
			client.queue.add(new ServerToClientMessage(winner, true));

		}

		@Override
		public void setPlayers(Player localPlayer, Player remotePlayer) {
			client.queue.add(new ServerToClientMessage(localPlayer, remotePlayer));

		}

		@Override
		public void onActivePlayer(Player activePlayer) {
			client.queue.add(new ServerToClientMessage(activePlayer, false));

		}

		@Override
		public void onTurnEnd(Player activePlayer, int turnNumber, TurnState turnState) {
			client.queue.add(new ServerToClientMessage(activePlayer, turnNumber, turnState));

		}

		@Override
		public void onUpdate(Player player1, Player player2, TurnState newState) {
			client.queue.add(new ServerToClientMessage(player1, player2, newState));

		}

		@Override
		public void onRequestAction(List<GameAction> availableActions) {
			client.queue.add(new ServerToClientMessage(availableActions));
		}

	}

}
