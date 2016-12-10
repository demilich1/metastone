package com.hiddenswitch.proto3.net.impl;

import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;
import com.hiddenswitch.proto3.net.Games;
import com.hiddenswitch.proto3.net.Matchmaking;
import com.hiddenswitch.proto3.net.Service;
import com.hiddenswitch.proto3.net.common.ClientToServerMessage;
import com.hiddenswitch.proto3.net.common.ServerGameContext;
import com.hiddenswitch.proto3.net.impl.server.GameSession;
import com.hiddenswitch.proto3.net.impl.server.ServerClientConnection;
import com.hiddenswitch.proto3.net.impl.server.ServerGameSession;
import com.hiddenswitch.proto3.net.impl.util.ActivityMonitor;
import com.hiddenswitch.proto3.net.models.*;
import com.hiddenswitch.proto3.net.util.ServiceProxy;
import com.hiddenswitch.proto3.net.util.Broker;
import com.hiddenswitch.proto3.net.util.IncomingMessage;
import com.hiddenswitch.proto3.net.util.Serialization;
import io.netty.channel.DefaultChannelId;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import io.vertx.ext.sync.Sync;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class GamesImpl extends Service<GamesImpl> implements Games {
	private Logger logger = LoggerFactory.getLogger(GamesImpl.class);

	public static final long DEFAULT_NO_ACTIVITY_TIMEOUT = 60 * 1000L;
	private static final long CLEANUP_DELAY_MILLISECONDS = 500L;

	private final Map<String, ServerGameSession> games = new HashMap<>();
	private final Map<NetSocket, ServerGameSession> gameForSocket = new HashMap<>();
	private final Map<NetSocket, IncomingMessage> messages = new HashMap<>();
	private final Map<String, ActivityMonitor> gameActivityMonitors = new HashMap<>();

	private NetServer server;
	private final int port;

	private ServiceProxy<Matchmaking> matchmaking;

	public GamesImpl() {
		this.port = RandomUtils.nextInt(6200, 16200);
	}

	@Override
	public void start(Future<Void> fut) {
		matchmaking = Broker.proxy(Matchmaking.class, vertx.eventBus());

		vertx.executeBlocking(blocking -> {
			try {
				CardCatalogue.loadCardsFromPackage();
			} catch (IOException | URISyntaxException | CardParseException e) {
				fut.fail(e);
			}

			DefaultChannelId.newInstance();
			// TODO: These ports shouldn't be totally randomized because of AWS security groups

			logger.info("Socket server configured.");
			blocking.complete();
		}, then -> {

			server = vertx.createNetServer();

			server.connectHandler(socket -> {
				socket.handler(Sync.fiberHandler(messageBuffer -> {
					receiveData(socket, messageBuffer);
				}));
			});

			server.listen(getPort(), getHost(), listenResult -> {
				if (!listenResult.succeeded()) {
					logger.error("Failure deploying socket listener: {}", listenResult.cause());
					fut.fail(listenResult.cause());
				} else {
					Broker.of(this, Games.class, vertx.eventBus());
					fut.complete();
				}
			});
		});
	}

	public ServerGameContext getGameContext(String gameId) {
		ServerGameSession session = this.getGames().get(gameId);
		if (session == null) {
			return null;
		}
		return session.getGameContext();
	}

	public GameSession getGameSession(String gameId) {
		return this
				.getGames()
				.getOrDefault(gameId, null);
	}

	@Override
	@Suspendable
	public ContainsGameSessionResponse containsGameSession(ContainsGameSessionRequest request) throws SuspendExecution {
		return new ContainsGameSessionResponse(this.getGames().containsKey(request.gameId));
	}

	@Override
	@Suspendable
	public CreateGameSessionResponse createGameSession(CreateGameSessionRequest request) throws SuspendExecution {
		if (request.getGameId() == null) {
			throw new RuntimeException("Game ID cannot be null in a create game session request.");
		}

		ServerGameSession session = new ServerGameSession(getHost(), getPort(), request.getPregame1(), request.getPregame2(), request.getGameId(), request.getNoActivityTimeout());
		session.handleGameOver(this::onGameOver);
		final String finalGameId = session.getGameId();
		games.put(finalGameId, session);
		// If the game has no activity after a certain amount of time, kill it automatically.
		gameActivityMonitors.put(finalGameId, new ActivityMonitor(vertx, finalGameId, request.getNoActivityTimeout(), this::kill));

		return new CreateGameSessionResponse(session.getConfigurationForPlayer1(), session.getConfigurationForPlayer2(), session.getGameId());
	}

	@Override
	public DescribeGameSessionResponse describeGameSession(DescribeGameSessionRequest request) {
		return DescribeGameSessionResponse.fromGameContext(getGameContext(request.getGameId()));
	}

	@Suspendable
	protected void receiveData(NetSocket socket, Buffer messageBuffer) {
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
				if (session.isGameReady()) {
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

	@Override
	public void stop(Future<Void> fut) {
		super.stop();
		getGames().values().forEach(ServerGameSession::kill);
		server.close(fut.completer());
	}

	@Override
	public EndGameSessionResponse endGameSession(EndGameSessionRequest request) {
		if (request.getGameId() == null) {
			throw new RuntimeException("Game ID cannot be null in an end game session request.");
		}

		this.kill(request.getGameId());

		return new EndGameSessionResponse();
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

		// Clear our maps of these socketsz
		sockets.forEach(s -> {
			gameForSocket.remove(s);
			messages.remove(s);
		});

		// Expire the match
		expireMatch(gameId);

		// Remove the game session
		games.remove(gameId);
	}

	public int getPort() {
		return port;
	}

	public String getHost() {
		// TODO: Look up this host correctly
		return "0.0.0.0";
	}

	public Map<String, ServerGameSession> getGames() {
		return Collections.unmodifiableMap(games);
	}

	private void expireMatch(String gameId) {
		matchmaking.async((AsyncResult<MatchExpireResponse> response) -> {
		}).expireMatch(new MatchExpireRequest(gameId));
	}

	private void onGameOver(ServerGameSession sgs) {
		final String gameOverId = sgs.getGameId();
		vertx.setTimer(CLEANUP_DELAY_MILLISECONDS, t -> {
			kill(gameOverId);
		});
	}
}
