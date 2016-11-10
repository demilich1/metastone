package com.hiddenswitch.proto3.net;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.amazon.GameRecord;
import com.hiddenswitch.proto3.net.amazon.MatchmakingRequestMessage;
import com.hiddenswitch.proto3.net.models.*;
import com.hiddenswitch.proto3.net.util.Serialization;
import com.hiddenswitch.proto3.server.GameSession;
import net.demilich.metastone.game.decks.Bench;
import net.demilich.metastone.game.decks.Deck;
import org.apache.commons.lang3.RandomStringUtils;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("/v1/games")
public class Games extends Service {
	public static final String MATCHMAKING_QUEUE = "matchmakingQueue";
	private Accounts accounts;
	private String matchmakingQueueUrl;

	public Games() {
	}

	public String getMatchmakingQueueUrl() {
		if (matchmakingQueueUrl == null) {
			setMatchmakingQueueUrl(getQueue().getQueueUrl(MATCHMAKING_QUEUE).getQueueUrl());
		}
		return matchmakingQueueUrl;
	}

	@PUT
	public MatchmakingResponse matchmakeAndJoin(MatchmakingRequest matchmakingRequest) {
		String userId = getAccounts().getUserId();
		MatchmakingResponse response = new MatchmakingResponse();
		MatchmakingRequestRetry retry = null;
		Game game = null;
		String gameId = null;
		// Is this a pending matchmaking request?
		if (matchmakingRequest.retry != null) {
			// Check if the game already has an opposing player.
			String retryGameId = matchmakingRequest.retry.getGameId();
			game = get(retryGameId);
			gameId = retryGameId;
		} else {
			// Try to find a game to join
			gameId = findGameIdAwaitingPlayer();

			// If there is no game to join, create a new one and set a retry request.
			if (gameId == null) {
				game = create();
			} else {
				game = get(gameId);
			}

			// Create a player record
			Bench deck = matchmakingRequest.deck;
			GamePlayer thisGamePlayer = createPlayer(userId, deck);
			game.setNullPlayer(thisGamePlayer);
			gameId = save(gameId, game);
		}

		if (game.isReadyToPlay()) {
			// The game is ready to play
			response.setConnection(getConnection(gameId, userId));
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

	public ClientConnectionConfiguration getConnection(String forGameId, String forUserId) {
		// Check if there is an existing session for this player
		GameRecord record = getDatabase().load(GameRecord.class, forGameId);
		if (record == null) {
			throw new IllegalArgumentException("Must specify a valid game ID.");
		}
		String gameSessionId = record.getGameSessionId();
		GameSession session = null;
		if (gameSessionId == null) {
			// Create a session for this game ID.
			session = createGameSession(record.getGame());
		} else {
			session = getGameSession(gameSessionId);
		}
		if (record.getGame().getGamePlayer1().getUserId().equals(forUserId)) {
			return session.getConfigurationForPlayer1();
		} else {
			return session.getConfigurationForPlayer2();
		}
	}

	private GameSession createGameSession(Game game) {
		return new GameSession(game.getGamePlayer1().getPregamePlayerConfig(), game.getGamePlayer2().getPregamePlayerConfig()) {
			@Override
			public ClientConnectionConfiguration getConfigurationForPlayer1() {
				throw new UnsupportedOperationException();
			}

			@Override
			public ClientConnectionConfiguration getConfigurationForPlayer2() {
				throw new UnsupportedOperationException();
			}
		};
	}

	private GameSession getGameSession(String gameSessionId) {
		return null;
	}

	/**
	 * Disposes a game's realtime resources, if any.
	 *
	 * @param gameId {String} The ID of the game to dispose.
	 */
	public void dispose(String gameId) {
		Game game = get(gameId);
	}

	private MatchmakingRequestRetry createRetry(String gameId) {
		MatchmakingRequestMessage matchmakingRequestMessage = new MatchmakingRequestMessage(gameId);
		SendMessageResult result = getQueue().sendMessage(getMatchmakingQueueUrl(), Serialization.serialize(matchmakingRequestMessage));
		MatchmakingRequestRetry retry = new MatchmakingRequestRetry(result.getMessageId(), gameId);
		retry.delayMilliseconds = 3000;
		return retry;
	}

	private GamePlayer createPlayer(String userId, Bench deck) {
		GamePlayer thisGamePlayer = new GamePlayer();
		thisGamePlayer.setUserId(userId);
		thisGamePlayer.setDeck(deck);
		thisGamePlayer.setProfile(getAccounts().getProfileForId(userId));
		return thisGamePlayer;
	}

	public Game get(String gameId) {
		GameRecord record = getDatabase().load(GameRecord.class, gameId);

		if (record == null) {
			return null;
		}

		return record.getGame();
	}

	private Game create() {
		Game game = new Game();
		return game;
	}

	private String save(String id, Game game) {
		if (id == null) {
			id = RandomStringUtils.randomAlphanumeric(36);
		}
		GameRecord record = new GameRecord(game);
		record.setId(id);
		getDatabase().save(record);
		return record.getId();
	}

	private String findGameIdAwaitingPlayer() {
		ReceiveMessageResult result = getQueue().receiveMessage(getMatchmakingQueueUrl());
		if (result.getMessages().isEmpty()) {
			return null;
		}
		Message message = result.getMessages().get(0);
		MatchmakingRequestMessage request = Serialization.deserialize(message.getBody(), MatchmakingRequestMessage.class);
		getQueue().deleteMessage(getMatchmakingQueueUrl(), message.getReceiptHandle());
		return request.getGameId();
	}

	public Accounts getAccounts() {
		return accounts;
	}

	public void setAccounts(Accounts accounts) {
		this.accounts = accounts;
	}

	public void setMatchmakingQueueUrl(String matchmakingQueueUrl) {
		this.matchmakingQueueUrl = matchmakingQueueUrl;
	}

}
