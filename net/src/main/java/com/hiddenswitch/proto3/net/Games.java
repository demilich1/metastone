package com.hiddenswitch.proto3.net;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.hiddenswitch.proto3.net.amazon.GameRecord;
import com.hiddenswitch.proto3.net.amazon.MatchmakingRequestMessage;
import com.hiddenswitch.proto3.net.models.*;
import com.hiddenswitch.proto3.net.util.Serialization;
import net.demilich.metastone.game.decks.Deck;
import org.apache.commons.lang3.RandomStringUtils;

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
			Deck deck = matchmakingRequest.deck;
			GamePlayer thisGamePlayer = createPlayer(userId, deck);
			game.setNullPlayer(thisGamePlayer);
			gameId = save(gameId, game);
		}

		if (game.isReadyToPlay()) {
			// The game is ready to play
			response.game = getGameCensored(userId, game);
			response.myChannelId = game.getPlayerForId(userId).getQueueUrl();
			response.retry = null;
		} else {
			// We're still waiting for a player
			response.game = null;
			response.myChannelId = null;
			if (matchmakingRequest.retry == null) {
				response.retry = createRetry(gameId);
			} else {
				response.retry = matchmakingRequest.retry;
			}
		}

		// Assert that all the fields in the response are set
		return response;
	}

	/**
	 * Disposes a game's realtime resources, if any.
	 *
	 * @param gameId {String} The ID of the game to dispose.
	 */
	public void dispose(String gameId) {
		Game game = get(gameId);

		for (String queueUrl : new String[]{game.getGamePlayer1().getQueueUrl(), game.getGamePlayer2().getQueueUrl()}) {
			getQueue().deleteQueue(queueUrl);
		}
	}

	private MatchmakingRequestRetry createRetry(String gameId) {
		MatchmakingRequestMessage matchmakingRequestMessage = new MatchmakingRequestMessage(gameId);
		SendMessageResult result = getQueue().sendMessage(getMatchmakingQueueUrl(), Serialization.serialize(matchmakingRequestMessage));
		MatchmakingRequestRetry retry = new MatchmakingRequestRetry(result.getMessageId(), gameId);
		retry.delayMilliseconds = 3000;
		return retry;
	}

	private GamePlayer createPlayer(String userId, Deck deck) {
		GamePlayer thisGamePlayer = new GamePlayer();
		thisGamePlayer.setUserId(userId);
		thisGamePlayer.setDeck(deck);
		thisGamePlayer.setProfile(getAccounts().getProfileForId(userId));
		thisGamePlayer.setQueueUrl(getQueueUrlForPlayer(userId, ChannelType.SQS));
		return thisGamePlayer;
	}

	private String getQueueUrlForPlayer(String userId, ChannelType type) {
		String queueName = userId.concat("_").concat(RandomStringUtils.randomAlphanumeric(36));
		String queueUrl = getQueue().createQueue(queueName).getQueueUrl();
		return queueUrl;
	}

	private Game getGameCensored(String forUserId, Game game) {
		// TODO: For now, just return the game as is.
		return game;
	}

	private Game get(String gameId) {
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
