package com.hiddenswitch.proto3.server;

import com.hiddenswitch.proto3.net.common.RemoteUpdateListener;
import com.hiddenswitch.proto3.net.common.ServerToClientMessage;
import com.hiddenswitch.proto3.net.common.ClientToServerMessage;
import com.hiddenswitch.proto3.net.common.MessageType;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.events.GameEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;

class SocketClientSession implements Runnable, RemoteUpdateListener {
	private Logger logger = LoggerFactory.getLogger(SocketClientSession.class);
	private WeakReference<SocketServerSession> socketServerSession;
	private WeakReference<ServerGameSession> serverGameSession;
	private Socket privateSocket;
	private BlockingQueue<ServerToClientMessage> queue = new LinkedBlockingQueue<>();

	SocketClientSession(SocketServerSession socketServerSession, Socket socket) {
		this.setSocketServerSession(socketServerSession);
		this.setPrivateSocket(socket);
	}

	@Override
	public void run() {
		logger.debug("Waiting for response from clients.");
		// Read thread
		new Thread(() -> {
			try {
				ObjectInputStream clientInputStream = new ObjectInputStream(getPrivateSocket().getInputStream());
				while (getSocketServerSession().isRunning() || getServerGameSession().isRunning()) {
					
					ClientToServerMessage message = (ClientToServerMessage) clientInputStream.readObject();

					if (message.getMt() == MessageType.FIRST_MESSAGE) {
						getSocketServerSession().getLock().lock();
						getSocketServerSession().onFirstMessage(this, message);
						getSocketServerSession().getLock().unlock();
					} else if (message.getMt() == MessageType.UPDATE_ACTION) {
						getServerGameSession().getListener().onActionRegistered(message.getCallingPlayer(), message.getAction());
					} else if (message.getMt() == MessageType.UPDATE_MULLIGAN) {
						getServerGameSession().getListener().onMulliganReceived(message.getPlayer1(), message.getDiscardedCards());
					} else {
						logger.error("Unexpected message type received from client. Message: {}", message);
					}
				}
				clientInputStream.close();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}).start();

		// Write thread
		new Thread(() -> {
			try {
				ObjectOutputStream clientOutputStream = new ObjectOutputStream(getPrivateSocket().getOutputStream());
				while (getSocketServerSession().isRunning()) {
					ServerToClientMessage message = getQueue().take();
					getGameLock().lock();
					logger.debug("Sending message: " + message.mt);
					clientOutputStream.writeObject(message);
					getGameLock().unlock();
					clientOutputStream.flush();
					//super important magic line below:
					clientOutputStream.reset();
				}
				clientOutputStream.close();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
				getGameLock().unlock();

			}

		}).start();
	}

	private Lock getGameLock() {
		return getServerGameSession().getLock();
	}

	private BlockingQueue<ServerToClientMessage> getQueue() {
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
		return socketServerSession.get();
	}

	private void setSocketServerSession(SocketServerSession socketServerSession) {
		this.socketServerSession = new WeakReference<SocketServerSession>(socketServerSession);
	}

	private Socket getPrivateSocket() {
		return privateSocket;
	}

	private void setPrivateSocket(Socket privateSocket) {
		this.privateSocket = privateSocket;
	}

	private ServerGameSession getServerGameSession() {
		return serverGameSession.get();
	}

	void setServerGameSession(ServerGameSession serverGameSession) {
		this.serverGameSession = new WeakReference<>(serverGameSession);
	}

	@Override
	public void onMulligan(Player player, List<Card> cards) {
		this.getQueue().add(new ServerToClientMessage(player, cards));
	}
}
