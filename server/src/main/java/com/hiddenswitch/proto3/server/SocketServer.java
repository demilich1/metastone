package com.hiddenswitch.proto3.server;

import com.hiddenswitch.proto3.net.common.ClientToServerMessage;
import com.hiddenswitch.proto3.net.common.NetworkBehaviour;
import com.hiddenswitch.proto3.net.util.IncomingMessage;
import com.hiddenswitch.proto3.net.util.Serialization;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class SocketServer extends AbstractVerticle {
	private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);
	private static final int DEFAULT_PORT = 11111;
	private final int port;
	private final Map<String, ServerGameSession> games = new HashMap<>();
	private final Map<NetSocket, ServerGameSession> gameForSocket = new HashMap<>();
	private final Map<NetSocket, IncomingMessage> messages = new HashMap<>();
	private NetServer server;

	@Override
	public void start() {
		server = vertx.createNetServer();
		server.connectHandler(socket -> {
			socket.handler(messageBuffer -> {
				receiveData(socket, messageBuffer);
			});
		});

		server.listen(getPort(), getHost(), listenResult -> {
			if (!listenResult.succeeded()) {
				getVertx().undeploy(deploymentID());
			}
		});
	}

	private void receiveData(NetSocket socket, Buffer messageBuffer) {
		logger.debug("Getting buffer from socket with hashCode {} length {}. Incoming message count: {}", socket.hashCode(), messageBuffer.length(), messages.size());
		// Do we have a reader for this socket?
		ClientToServerMessage message = null;
		int bytesRead = 0;
		Buffer remainder = null;
		if (!messages.containsKey(socket)) {
			try {
				IncomingMessage firstMessage = new IncomingMessage(messageBuffer);
				messages.put(socket, firstMessage);
				// Did we get a whole message in this buffer? If so, receive data again on the remainder.
				if (firstMessage.isComplete()) {
					Buffer twoMessages = firstMessage.getBufferWithoutHeader();
					logger.debug("Some remainder of a message was found.");
					remainder = twoMessages.getBuffer(firstMessage.getExpectedLength(), twoMessages.length());
				}
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		} else {
			IncomingMessage incomingMessage = messages.get(socket);
			bytesRead = incomingMessage.append(messageBuffer);
		}

		IncomingMessage incomingMessage = messages.get(socket);

		if (!incomingMessage.isComplete()) {
			return;
		}

		// If there appears to be data left over after finishing the message, hold onto the remainder of the buffer.
		if (bytesRead < messageBuffer.length()) {
			logger.debug("Some remainder of a message was found.");
			remainder = messageBuffer.getBuffer(bytesRead, messageBuffer.length());
		}

		try {
			message = deserializeMessage(incomingMessage);
		} catch (IOException | ClassNotFoundException e) {
			logger.error("Deserializing the message failed!", e);
		} finally {
			messages.remove(socket);
		}

		logger.debug("IncomingMessage complete on socket {}, expectedLength {} actual {}", socket.hashCode(), incomingMessage.getExpectedLength(), incomingMessage.getBufferWithoutHeader().length());

		if (message == null) {
			return;
		}

		ServerGameSession session;
		if (message.getGameId() != null) {
			session = getGames().get(message.getGameId());
		} else {
			session = gameForSocket.get(socket);
		}

		switch (message.getMt()) {
			case FIRST_MESSAGE:
				SocketClientReceiver client = new SocketClientReceiver(socket);
				if (session.getClient1() == null) {
					session.setClient1(client);
				} else {
					session.setClient2(client);
				}

				gameForSocket.put(socket, session);

				// Wrap the player's supplied behaviour as a NetworkBehaviour to make it work
				NetworkBehaviour networkBehaviour = new NetworkBehaviour(message.getPlayer1().getBehaviour());
				message.getPlayer1().setBehaviour(networkBehaviour);
				session.onPlayerConnected(message.getPlayer1());
				break;

			case UPDATE_ACTION:
				session.onActionReceived(message.getCallingPlayer(), message.getAction());
				break;

			case UPDATE_MULLIGAN:
				session.onMulliganReceived(message.getPlayer1(), message.getDiscardedCards());
				break;
		}

		if (remainder != null) {
			receiveData(socket, remainder);
		}
	}

	private ClientToServerMessage deserializeMessage(IncomingMessage incomingMessage) throws IOException, ClassNotFoundException {
		ClientToServerMessage message = null;
		message = Serialization.deserialize(incomingMessage.getBufferWithoutHeader().getBytes());
		return message;
	}

	public SocketServer() {
		this.port = DEFAULT_PORT;
	}

	public SocketServer(int port) {
		this.port = port;
	}

	@Override
	public void stop() {
		kill();
	}

	public void kill() {
		setRunning(false);
		getGames().values().forEach(ServerGameSession::kill);
	}

	public ServerGameSession createGameSession(PregamePlayerConfiguration player1, PregamePlayerConfiguration player2) {
		// Check if a session already exists for these two players
		ServerGameSession newSession = new ServerGameSession(getHost(), getPort(), player1, player2);
		games.put(newSession.getGameId(), newSession);
		return newSession;
	}

	public int getPort() {
		return port;
	}

	public String getHost() {
		return "localhost";
	}

	public Map<String, ServerGameSession> getGames() {
		return Collections.unmodifiableMap(games);
	}

	public void setRunning(boolean running) {
		if (!running) {
			kill();
		}
	}
}
