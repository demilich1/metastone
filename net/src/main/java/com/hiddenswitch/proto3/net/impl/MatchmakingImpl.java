package com.hiddenswitch.proto3.net.impl;

import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;
import com.hiddenswitch.proto3.net.Games;
import com.hiddenswitch.proto3.net.Matchmaking;
import com.hiddenswitch.proto3.net.Service;
import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.common.MatchmakingRequest;
import com.hiddenswitch.proto3.net.common.MatchmakingResponse;
import com.hiddenswitch.proto3.net.models.*;
import com.hiddenswitch.proto3.net.impl.util.Matchmaker;
import com.hiddenswitch.proto3.net.impl.server.GameSession;
import com.hiddenswitch.proto3.net.impl.server.PregamePlayerConfiguration;
import com.hiddenswitch.proto3.net.util.ServiceProxy;
import com.hiddenswitch.proto3.net.util.Broker;

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
		// TODO: Setup a user with a game against an AI if they've been waiting more than 10 seconds
		MatchmakingResponse response = new MatchmakingResponse();

		Matchmaker.Match match = matchmaker.match(userId, matchmakingRequest.deck);

		if (match == null) {
			response.setRetry(new MatchmakingRequest().withUserId(matchmakingRequest.userId));
			return response;
		}

		final ContainsGameSessionResponse contains = gameSessions.sync().containsGameSession(new ContainsGameSessionRequest(match.gameId));

		if (!contains.result) {
			// Create a game session.
			CreateGameSessionResponse createGameSessionResponse = gameSessions.sync().createGameSession(new CreateGameSessionRequest()
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

	@Override
	public MatchExpireResponse expireMatch(MatchExpireRequest request) {
		final MatchExpireResponse response = new MatchExpireResponse();
		response.expired = matchmaker.expire(request.gameId);
		return response;
	}
}
