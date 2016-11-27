package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.common.ServerGameContext;
import com.hiddenswitch.proto3.net.models.CreateGameSessionRequest;
import com.hiddenswitch.proto3.net.models.CreateGameSessionResponse;
import com.hiddenswitch.proto3.net.models.DescribeGameSessionRequest;
import com.hiddenswitch.proto3.net.models.DescribeGameSessionResponse;
import com.hiddenswitch.proto3.server.GameSession;
import com.hiddenswitch.proto3.server.ServerGameSession;
import com.hiddenswitch.proto3.server.SocketServer;
import io.netty.channel.DefaultChannelId;
import io.vertx.core.Vertx;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class GameSessions extends Service {
	private SocketServer server;
	private Thread serverThread;
	private Vertx vertx;

	public GameSessions() throws IOException, URISyntaxException, CardParseException {
		CardCatalogue.loadCardsFromPackage();
		DefaultChannelId.newInstance();
		int port = RandomUtils.nextInt(6200, 16200);
		vertx = Vertx.vertx();
		server = new SocketServer(port);
		vertx.deployVerticle(server);
	}

	public ServerGameContext getGameContext(String gameId) {
		return getServer()
				.getGames()
				.get(gameId)
				.getGameContext();
	}

	public GameSession getGameSession(String gameId) {
		return getServer()
				.getGames()
				.getOrDefault(gameId, null);
	}

	public SocketServer getServer() {
		return server;
	}

	public CreateGameSessionResponse createGameSession(CreateGameSessionRequest request) {
		ServerGameSession newSession = server.createGameSession(request.getPregame1(), request.getPregame2());
		return new CreateGameSessionResponse(newSession.getConfigurationForPlayer1(), newSession.getConfigurationForPlayer2(), newSession.getGameId());
	}

	public DescribeGameSessionResponse describeGameSession(DescribeGameSessionRequest request) {
		return DescribeGameSessionResponse.fromGameContext(getGameContext(request.getGameId()));
	}
}
