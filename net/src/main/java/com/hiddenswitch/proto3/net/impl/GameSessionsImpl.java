package com.hiddenswitch.proto3.net.impl;

import com.hiddenswitch.proto3.net.GameSessions;
import com.hiddenswitch.proto3.net.Service;
import com.hiddenswitch.proto3.net.common.ServerGameContext;
import com.hiddenswitch.proto3.net.models.*;
import com.hiddenswitch.proto3.server.GameSession;
import com.hiddenswitch.proto3.server.ServerGameSession;
import com.hiddenswitch.proto3.server.SocketServer;
import io.netty.channel.DefaultChannelId;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class GameSessionsImpl extends Service<GameSessionsImpl> implements GameSessions {
	private Logger logger = LoggerFactory.getLogger(GameSessionsImpl.class);
	private SocketServer server;

	public GameSessionsImpl() {
	}

	@Override
	public void start(Future<Void> result) {

		vertx.executeBlocking(blocking -> {
			try {
				CardCatalogue.loadCardsFromPackage();
			} catch (IOException | URISyntaxException | CardParseException e) {
				result.fail(e);
			}
			DefaultChannelId.newInstance();
			// TODO: These ports shouldn't be totally randomized because of AWS security groups
			int port = RandomUtils.nextInt(6200, 16200);
			server = new SocketServer(port);
			logger.info("Socket server configured.");

			blocking.complete();
		}, then -> vertx.deployVerticle(server, done -> {
			logger.info("Socket server deployed.");
			if (done.succeeded()) {
				result.complete();
			} else {
				result.fail(done.cause());
			}
		}));
	}

	public ServerGameContext getGameContext(String gameId) {
		ServerGameSession session = getServer().getGames().get(gameId);
		if (session == null) {
			return null;
		}
		return session.getGameContext();
	}

	public GameSession getGameSession(String gameId) {
		return getServer()
				.getGames()
				.getOrDefault(gameId, null);
	}

	public SocketServer getServer() {
		return server;
	}

	@Override
	public ContainsGameSessionResponse containsGameSession(ContainsGameSessionRequest request) {
		return new ContainsGameSessionResponse(getServer().getGames().containsKey(request.gameId));
	}

	@Override
	public CreateGameSessionResponse createGameSession(CreateGameSessionRequest request) {
		if (request.getGameId() == null) {
			throw new RuntimeException("Game ID cannot be null in a create game session request.");
		}
		ServerGameSession newSession = server.createGameSession(request.getPregame1(), request.getPregame2(), request.getGameId(), request.getNoActivityTimeout());
		return new CreateGameSessionResponse(newSession.getConfigurationForPlayer1(), newSession.getConfigurationForPlayer2(), newSession.getGameId());
	}

	@Override
	public DescribeGameSessionResponse describeGameSession(DescribeGameSessionRequest request) {
		return DescribeGameSessionResponse.fromGameContext(getGameContext(request.getGameId()));
	}

	@Override
	public void stop() {
		super.stop();
	}

	@Override
	public EndGameSessionResponse endGameSession(EndGameSessionRequest request) {
		if (request.getGameId() == null) {
			throw new RuntimeException("Game ID cannot be null in an end game session request.");
		}

		getServer().kill(request.getGameId());

		return new EndGameSessionResponse();
	}
}
