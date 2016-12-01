package com.hiddenswitch.proto3.net;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.hiddenswitch.proto3.net.amazon.GameRecord;
import com.hiddenswitch.proto3.net.amazon.MatchmakingRequestMessage;
import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.models.*;
import com.hiddenswitch.proto3.net.util.Matchmaker;
import com.hiddenswitch.proto3.net.util.Serialization;
import com.hiddenswitch.proto3.server.GameSession;
import com.hiddenswitch.proto3.server.PregamePlayerConfiguration;
import io.vertx.core.Future;
import net.demilich.metastone.game.decks.Deck;
import org.apache.commons.collections.FastTreeMap;
import org.apache.commons.collections.list.TreeList;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Games extends Service<Games> {
	private GameSessions gameSessions;
	private Matchmaker matchmaker = new Matchmaker();
	private Map<String, ClientConnectionConfiguration> connections = new HashMap<>();

	@Override
	public void start(Future<Void> done) {
		if (gameSessions == null) {
			// TODO: Look up the verticle on the verticle service thing
			done.fail(new NullPointerException("gameSessions wasn't configured when the Games verticle was started. Please inject this dependency."));
		} else {
			done.complete();
		}
	}

	@Override
	public void stop() {
		super.stop();
	}

	public MatchmakingResponse matchmakeAndJoin(MatchmakingRequest matchmakingRequest, String userId) {
		// TODO: Setup a user with a game against an AI if they've been waiting more than 10 seconds
		MatchmakingResponse response = new MatchmakingResponse();

		Matchmaker.Match match = matchmaker.match(userId, matchmakingRequest.deck);

		if (match == null) {
			response.setRetry(new MatchmakingRequest());
			return response;
		}

		if (getGameSessions().getGameSession(match.gameId) == null) {
			// Create a game session.
			CreateGameSessionResponse createGameSessionResponse = gameSessions.createGameSession(new CreateGameSessionRequest()
					.withPregame1(new PregamePlayerConfiguration(match.entry1.deck, match.entry1.userId))
					.withPregame2(new PregamePlayerConfiguration(match.entry2.deck, match.entry2.userId))
					.withGameId(match.gameId));
			GameSession session = createGameSessionResponse.toSession();
			connections.put(match.entry1.userId, session.getConfigurationForPlayer1());
			connections.put(match.entry2.userId, session.getConfigurationForPlayer2());
		}

		response.setConnection(connections.get(userId));
		return response;
	}
	public GameSessions getGameSessions() {
		return gameSessions;
	}

	public void setGameSessions(GameSessions gameSessions) {
		this.gameSessions = gameSessions;
	}

	public Games withGameSessions(GameSessions gameSessions) {
		setGameSessions(gameSessions);
		return this;
	}
}
