package com.hiddenswitch.proto3.net;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.hiddenswitch.proto3.net.amazon.GameRecord;
import com.hiddenswitch.proto3.net.amazon.MatchmakingRequestMessage;
import com.hiddenswitch.proto3.net.models.*;
import com.hiddenswitch.proto3.net.util.Serialization;
import net.demilich.metastone.game.decks.Deck;

import java.util.UUID;

/**
 * Created by bberman on 10/9/16.
 */
public class Games extends Service {
	private Accounts accounts;
	private String matchmakingQueueUrl;

	public Games() {
		super();
		setAccounts(new Accounts());
		setMatchmakingQueueUrl(getQueue().getQueueUrl("matchmakingQueue").getQueueUrl());
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
			response.myChannelId = game.getPlayerForId(userId).channelId;
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

	private MatchmakingRequestRetry createRetry(String gameId) {
		// TODO: Create the queue

		MatchmakingRequestMessage matchmakingRequestMessage = new MatchmakingRequestMessage(gameId);
		SendMessageResult result = getQueue().sendMessage(getMatchmakingQueueUrl(), Serialization.serialize(matchmakingRequestMessage));
		MatchmakingRequestRetry retry = new MatchmakingRequestRetry(gameId, result.getMessageId());
		retry.delayMilliseconds = 3000;
		return retry;
	}

	private GamePlayer createPlayer(String userId, Deck deck) {
		GamePlayer thisGamePlayer = new GamePlayer();
		thisGamePlayer.userId = userId;
		thisGamePlayer.deck = deck;
		thisGamePlayer.profile = getAccounts().getProfileForId(userId);
		thisGamePlayer.channelId = createChannel(userId, ChannelType.SQS);
		return thisGamePlayer;
	}

	private String createChannel(String userId, ChannelType type) {
		String queueName = userId.concat(":").concat(UUID.randomUUID().toString());
		getQueue().createQueue(queueName);
		return queueName;
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

	public String getMatchmakingQueueUrl() {
		return matchmakingQueueUrl;
	}

	public void setMatchmakingQueueUrl(String matchmakingQueueUrl) {
		this.matchmakingQueueUrl = matchmakingQueueUrl;
	}

}
