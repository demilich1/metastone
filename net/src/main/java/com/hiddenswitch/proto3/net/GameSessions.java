package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.common.ServerGameContext;
import com.hiddenswitch.proto3.net.models.CreateGameSessionRequest;
import com.hiddenswitch.proto3.net.models.CreateGameSessionResponse;
import com.hiddenswitch.proto3.net.models.DescribeGameSessionRequest;
import com.hiddenswitch.proto3.net.models.DescribeGameSessionResponse;
import com.hiddenswitch.proto3.server.GameSession;
import com.hiddenswitch.proto3.server.ServerGameSession;
import com.hiddenswitch.proto3.server.SocketServerSession;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class GameSessions extends Service {
	private SocketServerSession server;
	private Thread serverThread;

	public GameSessions() throws IOException, URISyntaxException, CardParseException {
		CardCatalogue.loadCardsFromPackage();
		int port = RandomUtils.nextInt(6200, 16200);
		server = new SocketServerSession(port);
		serverThread = new Thread(server);
		serverThread.start();
	}

	public ServerGameContext getGameContext(String gameId) {
		return getServer()
				.getGames()
				.get(gameId)
				.getListener()
				.getGameContext();
	}

	public GameSession getGameSession(String gameId) {
		return getServer()
				.getGames()
				.getOrDefault(gameId, null);
	}

	public SocketServerSession getServer() {
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
