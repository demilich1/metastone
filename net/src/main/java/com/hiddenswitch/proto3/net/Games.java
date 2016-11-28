package com.hiddenswitch.proto3.net;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.hiddenswitch.proto3.net.amazon.GameRecord;
import com.hiddenswitch.proto3.net.amazon.MatchmakingRequestMessage;
import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.models.*;
import com.hiddenswitch.proto3.net.util.Serialization;
import com.hiddenswitch.proto3.server.GameSession;
import io.vertx.core.Future;
import net.demilich.metastone.game.decks.Deck;
import org.apache.commons.lang3.RandomStringUtils;

public class Games extends Service<Games> {
	public static final String MATCHMAKING_QUEUE = "matchmakingQueue";
	private GameSessions gameSessions;
	private String matchmakingQueueUrl;

	public String getMatchmakingQueueUrl() {
		if (matchmakingQueueUrl == null) {
			setMatchmakingQueueUrl(getQueue().getQueueUrl(MATCHMAKING_QUEUE).getQueueUrl());
		}
		return matchmakingQueueUrl;
	}

	@Override
	public void start(Future<Void> done) {
		if (gameSessions == null) {
			// TODO: Look up the verticle on the verticle service thing
			done.fail(new NullPointerException("gameSessions wasn't configured when the Games verticle was started. Please inject this dependency."));
		} else {
			done.complete();
		}
	}

	public MatchmakingResponse matchmakeAndJoin(MatchmakingRequest matchmakingRequest, String userId) {
		MatchmakingResponse response = new MatchmakingResponse();
		GameRecord record;
		String gameId;
		// Is this a pending matchmaking request?
		if (matchmakingRequest.retry != null) {
			// Check if the game already has an opposing player.
			String retryGameId = matchmakingRequest.retry.getGameId();
			record = get(retryGameId);
			gameId = retryGameId;
		} else {
			// Try to find a game to join
			gameId = findGameIdAwaitingPlayer();

			// If there is no game to join, create a new one and set a retry request.
			if (gameId == null) {
				record = new GameRecord(new Game());
				gameId = RandomStringUtils.randomAlphanumeric(40);
				record.setId(gameId);
			} else {
				record = get(gameId);
			}

			// Create a player record
			Deck deck = matchmakingRequest.deck;
			GamePlayer thisGamePlayer = createPlayer(userId, deck);
			record.getGame().setNullPlayer(thisGamePlayer);
			record.setId(gameId);
			gameId = save(record);
		}

		if (record.getGame().isReadyToPlay()) {
			// The game is ready to play, so create a game if it doesn't exist.
			ClientConnectionConfiguration result;
			String gameSessionId = record.getGameSessionId();
			boolean isPlayer1 = record.getGame().getGamePlayer1().getUserId().equals(userId);
			GameSession session = null;

			if (gameSessionId == null) {
				// Create a session for this game ID.
				Game game1 = record.getGame();
				// Create a game session.
				CreateGameSessionResponse createGameSessionResponse = gameSessions.createGameSession(new CreateGameSessionRequest()
						.withPregame1(game1.getGamePlayer1().getPregamePlayerConfig())
						.withPregame2(game1.getGamePlayer2().getPregamePlayerConfig()));
				// Save the information the record needs
				session = createGameSessionResponse.toSession();
				record.getGame().getGamePlayer1().setConnectionConfiguration(session.getConfigurationForPlayer1());
				record.getGame().getGamePlayer2().setConnectionConfiguration(session.getConfigurationForPlayer2());
				record.setGameSessionId(session.getGameId());
				save(record);
			} else {
				session = getGameSessions().getGameSession(gameSessionId);
			}

			if (isPlayer1) {
				result = session.getConfigurationForPlayer1();
			} else {
				result = session.getConfigurationForPlayer2();
			}

			ClientConnectionConfiguration connection = result;
			response.setConnection(connection);
			response.setRetry(null);
		} else {
			// We're still waiting for a player
			response.setConnection(null);
			if (matchmakingRequest.retry == null) {
				response.setRetry(createRetry(gameId));
			} else {
				response.setRetry(matchmakingRequest.retry);
			}
		}

		// Assert that all the fields in the response are set
		return response;
	}

	protected MatchmakingRequestRetry createRetry(String gameId) {
		MatchmakingRequestMessage matchmakingRequestMessage = new MatchmakingRequestMessage(gameId);
		SendMessageResult result = getQueue().sendMessage(getMatchmakingQueueUrl(), Serialization.serialize(matchmakingRequestMessage));
		MatchmakingRequestRetry retry = new MatchmakingRequestRetry(result.getMessageId(), gameId);
		retry.delayMilliseconds = 3000;
		return retry;
	}

	protected GamePlayer createPlayer(String userId, Deck deck) {
		GamePlayer thisGamePlayer = new GamePlayer();
		thisGamePlayer.setUserId(userId);
		thisGamePlayer.setDeck(deck);
		return thisGamePlayer;
	}

	protected GameRecord get(String gameId) {
		return getDatabase().load(GameRecord.class, gameId);
	}

	protected String save(GameRecord record) {
		getDatabase().save(record);
		return record.getId();
	}

	protected String findGameIdAwaitingPlayer() {
		ReceiveMessageResult result = getQueue().receiveMessage(getMatchmakingQueueUrl());
		if (result.getMessages().isEmpty()) {
			return null;
		}
		Message message = result.getMessages().get(0);
		MatchmakingRequestMessage request = Serialization.deserialize(message.getBody(), MatchmakingRequestMessage.class);
		getQueue().deleteMessage(getMatchmakingQueueUrl(), message.getReceiptHandle());
		return request.getGameId();
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

	public void setMatchmakingQueueUrl(String matchmakingQueueUrl) {
		this.matchmakingQueueUrl = matchmakingQueueUrl;
	}

}
