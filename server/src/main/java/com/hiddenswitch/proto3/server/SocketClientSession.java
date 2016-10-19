package com.hiddenswitch.proto3.server;

import com.hiddenswitch.proto3.MetaStoneSimpleServer;
import com.hiddenswitch.proto3.common.ServerToClientMessage;
import com.hiddenswitch.proto3.common.ClientToServerMessage;
import com.hiddenswitch.proto3.common.MessageType;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.events.GameEvent;
import com.hiddenswitch.proto3.common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class SocketClientSession implements Runnable, RemoteUpdateListener {
	private SocketServerSession socketServerSession;
	private Socket privateSocket;
	private BlockingQueue<ServerToClientMessage> queue = new LinkedBlockingQueue<>();

	SocketClientSession(SocketServerSession socketServerSession, Socket socket) {
		this.setSocketServerSession(socketServerSession);
		this.setPrivateSocket(socket);
	}

	@Override
	public void run() {
		System.out.println("waiting for response from clients");
		//Read thread
		new Thread(() -> {
			try {
				ObjectInputStream clientInputStream = new ObjectInputStream(getPrivateSocket().getInputStream());
				while (getSocketServerSession().shouldRun) {

					ClientToServerMessage message = (ClientToServerMessage) clientInputStream.readObject();
					System.out.println("message received");
					System.out.println(message.getMt().toString());
					if (message.getMt() == MessageType.REGISTER_PLAYER) {
						getSocketServerSession().listener.onPlayerConnected(message.getPlayer1(), message.getPlayer2());
					} else {
						getSocketServerSession().listener.onActionRegistered(message.getCallingPlayer(), message.getAction());
					}
				}
				clientInputStream.close();
				getSocketServerSession().socketConnection.close();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}).start();

		//Write thread
		new Thread(() -> {
			try {
				ObjectOutputStream clientOutputStream = new ObjectOutputStream(getPrivateSocket().getOutputStream());
				while (getSocketServerSession().shouldRun) {
					ServerToClientMessage message = getQueue().take();
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

		}).start();
	}

	public BlockingQueue<ServerToClientMessage> getQueue() {
		return queue;
	}

	public void setQueue(BlockingQueue<ServerToClientMessage> queue) {
		this.queue = queue;
	}

	@Override
	public void onGameEvent(GameEvent event) {
		this.getQueue().add(new ServerToClientMessage(event));

	}

	@Override
	public void onGameEnd(Player winner) {
		this.getQueue().add(new ServerToClientMessage(winner, true));

	}

	@Override
	public void setPlayers(Player localPlayer, Player remotePlayer) {
		this.getQueue().add(new ServerToClientMessage(localPlayer, remotePlayer));

	}

	@Override
	public void onActivePlayer(Player activePlayer) {
		this.getQueue().add(new ServerToClientMessage(activePlayer, false));
	}

	@Override
	public void onTurnEnd(Player activePlayer, int turnNumber, TurnState turnState) {
		this.getQueue().add(new ServerToClientMessage(activePlayer, turnNumber, turnState));

	}

	@Override
	public void onUpdate(Player player1, Player player2, TurnState newState) {
		this.getQueue().add(new ServerToClientMessage(player1, player2, newState));
	}

	@Override
	public void onRequestAction(List<GameAction> availableActions) {
		this.getQueue().add(new ServerToClientMessage(availableActions));
	}

	private SocketServerSession getSocketServerSession() {
		return socketServerSession;
	}

	private void setSocketServerSession(SocketServerSession socketServerSession) {
		this.socketServerSession = socketServerSession;
	}

	private Socket getPrivateSocket() {
		return privateSocket;
	}

	private void setPrivateSocket(Socket privateSocket) {
		this.privateSocket = privateSocket;
	}
}
