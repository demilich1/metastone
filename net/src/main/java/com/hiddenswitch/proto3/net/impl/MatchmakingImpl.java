package com.hiddenswitch.proto3.net.impl;

import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;
import com.hiddenswitch.proto3.net.Games;
import com.hiddenswitch.proto3.net.Matchmaking;
import com.hiddenswitch.proto3.net.Service;
import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.common.MatchmakingRequest;
import com.hiddenswitch.proto3.net.common.MatchmakingResponse;
import com.hiddenswitch.proto3.net.impl.util.QueueEntry;
import com.hiddenswitch.proto3.net.models.*;
import com.hiddenswitch.proto3.net.impl.util.Matchmaker;
import com.hiddenswitch.proto3.net.impl.server.GameSession;
import com.hiddenswitch.proto3.net.impl.server.PregamePlayerConfiguration;
import com.hiddenswitch.proto3.net.util.ServiceProxy;
import com.hiddenswitch.proto3.net.util.Broker;
import net.demilich.metastone.game.decks.DeckFactory;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

public class MatchmakingImpl extends Service<MatchmakingImpl> implements Matchmaking {
	private ServiceProxy<Games> gameSessions;
	private Matchmaker matchmaker = new Matchmaker();
	private Map<String, ClientConnectionConfiguration> connections = new HashMap<>();

	@Override
	public void start() {
		if (gameSessions == null) {
			gameSessions = Broker.proxy(Games.class, vertx.eventBus());
		}

		Broker.of(this, Matchmaking.class, vertx.eventBus());
	}

	@Override
	public void stop() {
		super.stop();
	}

	@Override
	public MatchCancelResponse cancel(MatchCancelRequest matchCancelRequest) {
		return new MatchCancelResponse(matchmaker.remove(matchCancelRequest.getUserId()));
	}

	@Override
	@Suspendable
	public MatchmakingResponse matchmakeAndJoin(MatchmakingRequest matchmakingRequest) throws InterruptedException, SuspendExecution {
		final String userId = matchmakingRequest.userId;
		MatchmakingResponse response = new MatchmakingResponse();

		final boolean isWaitingTooLong = matchmaker.contains(userId)
				&& matchmaker.get(userId).createdAt + (long) 25e9 < System.nanoTime();

		// TODO: Deal with reconnecting to AI game

		// Setup a user with a game against an AI if they've been waiting more than 10 seconds
		if (isWaitingTooLong) {
			QueueEntry entry = matchmaker.get(userId);
			String gameId = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
			String aiUserId = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
			// The player has been waiting too long. Match to an AI.
			final CreateGameSessionRequest request = new CreateGameSessionRequest()
					.withPregame1(new PregamePlayerConfiguration(entry.deck, entry.userId))
					// TODO: Pick a better deck for the AI
					.withPregame2(new PregamePlayerConfiguration(DeckFactory.getRandomDeck(), aiUserId)
							.withAI(true))
					.withGameId(gameId);

			CreateGameSessionResponse createGameSessionResponse = gameSessions.sync().createGameSession(request);

			GameSession session = createGameSessionResponse.toSession();
			final ClientConnectionConfiguration connection = session.getConfigurationForPlayer1();
			connections.put(userId, connection);
			matchmaker.remove(userId);
			response.setConnection(connection);
			return response;
		}

		Matchmaker.Match match = matchmaker.match(userId, matchmakingRequest.deck);

		if (match == null) {
			response.setRetry(new MatchmakingRequest().withUserId(matchmakingRequest.userId));
			return response;
		}

		final ContainsGameSessionResponse contains = gameSessions.sync().containsGameSession(new ContainsGameSessionRequest(match.gameId));

		if (!contains.result) {
			// Create a game session.
			final CreateGameSessionRequest request = new CreateGameSessionRequest()
					.withPregame1(new PregamePlayerConfiguration(match.entry1.deck, match.entry1.userId))
					.withPregame2(new PregamePlayerConfiguration(match.entry2.deck, match.entry2.userId))
					.withGameId(match.gameId);
			CreateGameSessionResponse createGameSessionResponse = gameSessions.sync().createGameSession(request);
			GameSession session = createGameSessionResponse.toSession();
			connections.put(match.entry1.userId, session.getConfigurationForPlayer1());
			connections.put(match.entry2.userId, session.getConfigurationForPlayer2());
		}

		response.setConnection(connections.get(userId));
		return response;
	}

	@Override
	public MatchExpireResponse expireMatch(MatchExpireRequest request) {
		// TODO: Clear out old connections from AI games
		final MatchExpireResponse response = new MatchExpireResponse();
		Matchmaker.Match match = matchmaker.asMatches().get(request.gameId);
		if (match != null) {
			connections.remove(match.entry1.userId);
			connections.remove(match.entry2.userId);
		}
		response.expired = matchmaker.expire(request.gameId);
		return response;
	}
}
