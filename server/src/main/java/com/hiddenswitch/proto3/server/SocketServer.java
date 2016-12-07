package com.hiddenswitch.proto3.server;

import co.paralleluniverse.fibers.Suspendable;
import com.hiddenswitch.proto3.net.common.ClientToServerMessage;
import com.hiddenswitch.proto3.net.util.IncomingMessage;
import com.hiddenswitch.proto3.net.util.Serialization;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import io.vertx.ext.sync.Sync;
import io.vertx.ext.sync.SyncVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class SocketServer extends SyncVerticle {
	private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);
	private static final int DEFAULT_PORT = 11111;
	public static final long DEFAULT_NO_ACTIVITY_TIMEOUT = 60 * 1000L;
	private final int port;
	private final Map<String, ServerGameSession> games = new HashMap<>();
	private final Map<NetSocket, ServerGameSession> gameForSocket = new HashMap<>();
	private final Map<NetSocket, IncomingMessage> messages = new HashMap<>();
	private final Map<String, ActivityMonitor> gameActivityMonitors = new HashMap<>();
	private final long cleanupDelayMilliseconds = 500L;
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
		logger.trace("Getting buffer from socket with hashCode {} length {}. Incoming message count: {}", socket.hashCode(), messageBuffer.length(), messages.size());
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
			logger.trace("Some remainder of a message was found. Bytes read: {}, remainder: {}", bytesRead, messageBuffer.length() - bytesRead);
			remainder = messageBuffer.getBuffer(bytesRead, messageBuffer.length());
		}

		try {
			message = Serialization.deserialize(incomingMessage.getBufferWithoutHeader().getBytes());
		} catch (IOException | ClassNotFoundException e) {
			logger.error("Deserializing the message failed!", e);
		} catch (Exception e) {
			logger.error("A different deserialization error occurred!", e);
		} finally {
			messages.remove(socket);
		}

		logger.trace("IncomingMessage complete on socket {}, expectedLength {} actual {}", socket.hashCode(), incomingMessage.getExpectedLength(), incomingMessage.getBufferWithoutHeader().length());

		if (message == null) {
			return;
		}

		ServerGameSession session;
		if (message.getGameId() != null) {
			session = getGames().get(message.getGameId());
		} else {
			session = gameForSocket.get(socket);
		}

		// Show activity on the game activity monitor
		gameActivityMonitors.get(session.getGameId()).activity();

		switch (message.getMt()) {
			case FIRST_MESSAGE:
				logger.debug("First message received from {}", message.getPlayer1().toString());
				ServerClientConnection client = new ServerClientConnection(socket);
				gameForSocket.put(socket, session);
				// Is this a reconnect?
				if (session.areBothPlayersJoined()) {
					// TODO: Remove references to the old socket
					// Replace the client
					session.onPlayerReconnected(message.getPlayer1(), client);
				} else {
					logger.debug("Calling onPlayerConnected for {}, {}", toString(), message.getPlayer1().toString());
					session.onPlayerConnected(message.getPlayer1(), client);
				}
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

	@Suspendable
	public void kill(String gameId) {
		ServerGameSession session = games.get(gameId);

		if (session == null) {
			// The session was already removed
			return;
		}

		// Clear out the activity monitors
		gameActivityMonitors.get(gameId).cancel();
		gameActivityMonitors.remove(gameId);

		// Get the sockets associated with the session
		List<NetSocket> sockets = Arrays.asList(session.getClient1().getPrivateSocket(), session.getClient2().getPrivateSocket());

		// Kill the session
		session.kill();

		// Clear our maps of these sockets
		sockets.forEach(s -> {
			gameForSocket.remove(s);
			messages.remove(s);
		});

		// Expire the match
		expireMatch(gameId);

		// Remove the game session
		games.remove(gameId);
	}

	public ServerGameSession createGameSession(PregamePlayerConfiguration player1, PregamePlayerConfiguration player2, String gameId) {
		return createGameSession(player1, player2, gameId, DEFAULT_NO_ACTIVITY_TIMEOUT);
	}

	public ServerGameSession createGameSession(PregamePlayerConfiguration player1, PregamePlayerConfiguration player2, String gameId, long noActivityTimeout) {
		ServerGameSession newSession = new ServerGameSession(getHost(), getPort(), player1, player2, gameId, noActivityTimeout);
		newSession.handleGameOver(this::onGameOver);
		final String finalGameId = newSession.getGameId();
		games.put(finalGameId, newSession);
		// If the game has no activity after a certain amount of time, kill it automatically.
		gameActivityMonitors.put(finalGameId, new ActivityMonitor(vertx, finalGameId, noActivityTimeout, this::kill));
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

	private void expireMatch(String gameId) {
		EventBus eb = vertx.eventBus();
		JsonObject message = new JsonObject();
		message.put("gameId", gameId);
		eb.send("Games::expireMatch", message, reply -> {
			if (reply.failed()) {
				// Nobody was listening, the matchmaking service isn't running
			} else {
				final JsonObject body = (JsonObject) reply.result().body();
				if (!body.getBoolean("expired")) {
					// TODO: Do something if we failed to expire the message.
				}
			}
		});
	}

	private void onGameOver(ServerGameSession sgs) {
		final String gameOverId = sgs.getGameId();
		vertx.setTimer(cleanupDelayMilliseconds, t -> {
			kill(gameOverId);
		});
	}
}
