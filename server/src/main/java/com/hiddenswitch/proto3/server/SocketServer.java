package com.hiddenswitch.proto3.server;

import co.paralleluniverse.fibers.Suspendable;
import com.hiddenswitch.proto3.net.common.ClientToServerMessage;
import com.hiddenswitch.proto3.net.util.IncomingMessage;
import com.hiddenswitch.proto3.net.util.Serialization;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import io.vertx.ext.sync.Sync;
import io.vertx.ext.sync.SyncVerticle;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SocketServer extends SyncVerticle {
	private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);
	private static final int DEFAULT_PORT = 11111;
	private final int port;
	private final Map<String, ServerGameSession> games = new HashMap<>();
	private final Map<NetSocket, ServerGameSession> gameForSocket = new HashMap<>();
	private final Map<NetSocket, IncomingMessage> messages = new HashMap<>();
	private NetServer server;

	@Override
	@Suspendable
	public void start() {
		server = vertx.createNetServer();
		NetServer result = Sync.awaitResult(done -> {
			server.connectHandler(socket -> {
				socket.handler(Sync.fiberHandler(messageBuffer -> {
					receiveData(socket, messageBuffer);
				}));
			});

			server.listen(getPort(), getHost(), listenResult -> {
				if (!listenResult.succeeded()) {
					logger.error("Failure deploying socket listener: {}", listenResult.cause());
				}
				done.handle(listenResult);
			});
		});
	}

	@Suspendable
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
				bytesRead = firstMessage.getBufferWithoutHeader().length() + IncomingMessage.HEADER_SIZE;
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		} else {
			bytesRead = messages.get(socket).append(messageBuffer);
		}

		IncomingMessage incomingMessage = messages.get(socket);

		if (!incomingMessage.isComplete()) {
			return;
		}

		// If there appears to be data left over after finishing the message, hold onto the remainder of the buffer.
		if (bytesRead < messageBuffer.length()) {
			logger.debug("Some remainder of a message was found. Bytes read: {}, remainder: {}", bytesRead, messageBuffer.length() - bytesRead);
			remainder = messageBuffer.getBuffer(bytesRead, messageBuffer.length());
		}

		try {
			message = deserializeMessage(incomingMessage);
		} catch (IOException | ClassNotFoundException e) {
			logger.error("Deserializing the message failed!", e);
		} catch (Exception e) {
			logger.error("A different deserialization error occurred!");
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
				logger.debug("First message received from {}", message.getPlayer1().toString());
				SocketClientReceiver client = new SocketClientReceiver(socket);
				if (session.getClient1() == null) {
					session.setClient1(client);
				} else {
					session.setClient2(client);
				}

				gameForSocket.put(socket, session);

				logger.debug("Calling onPlayerConnected for {}, {}", toString(), message.getPlayer1().toString());
				session.onPlayerConnected(message.getPlayer1());
				break;

			case UPDATE_ACTION:
				logger.debug("Server received message with ID {} action {}", message.getId(), message.getAction());
				session.onActionReceived(message.getId(), message.getAction());
				break;

			case UPDATE_MULLIGAN:
				session.onMulliganReceived(message.getId(), message.getPlayer1(), message.getDiscardedCards());
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
	@Suspendable
	public void stop() {
		getGames().values().forEach(ServerGameSession::kill);
		Void t = Sync.awaitResult(done -> server.close(done));
	}

	public ServerGameSession createGameSession(PregamePlayerConfiguration player1, PregamePlayerConfiguration player2, String gameId) {
		ServerGameSession newSession = new ServerGameSession(getHost(), getPort(), player1, player2, gameId);
		games.put(newSession.getGameId(), newSession);
		return newSession;
	}

	public int getPort() {
		return port;
	}

	public String getHost() {
		return "0.0.0.0";
	}

	public Map<String, ServerGameSession> getGames() {
		return Collections.unmodifiableMap(games);
	}
}
