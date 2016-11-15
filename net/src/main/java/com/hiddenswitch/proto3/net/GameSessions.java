package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.models.CreateGameSessionRequest;
import com.hiddenswitch.proto3.net.models.CreateGameSessionResponse;
import com.hiddenswitch.proto3.server.ServerGameSession;
import com.hiddenswitch.proto3.server.SocketServerSession;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import org.apache.commons.lang3.RandomUtils;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.io.IOException;
import java.net.URISyntaxException;

@Path("/v1/gamesessions")
public class GameSessions extends Service {
	private static SocketServerSession server;
	private static Thread serverThread;

	public GameSessions() throws IOException, URISyntaxException, CardParseException {
		super();
		if (server == null) {
			CardCatalogue.copyCardsFromResources();
			CardCatalogue.loadCardsFromFilesystem();
			int port = RandomUtils.nextInt(6200, 16200);
			server = new SocketServerSession(port);
			serverThread = new Thread(server);
			serverThread.start();
		}
	}

	@POST
	public CreateGameSessionResponse createGameSession(CreateGameSessionRequest request) {
		ServerGameSession newSession = server.createGameSession(request.getPregame1(), request.getPregame2());
		return new CreateGameSessionResponse(newSession.getConfigurationForPlayer1(), newSession.getConfigurationForPlayer2());
	}
}
