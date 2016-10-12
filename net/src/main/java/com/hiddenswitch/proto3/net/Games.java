package com.hiddenswitch.proto3.net;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.hiddenswitch.proto3.net.models.*;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.decks.Deck;

import java.util.UUID;

/**
 * Created by bberman on 10/9/16.
 */
public class Games {
	public MatchmakingResponse matchmakeAndJoin(MatchmakingRequest matchmakingRequest) {
		Accounts accounts = new Accounts();
		String userId = accounts.getUserId();
		MatchmakingResponse response = new MatchmakingResponse();
		MatchmakingRequestRetry retry = null;
		Game game = null;
		// Is this a pending matchmaking request?
		if (matchmakingRequest.retry != null) {
			// Check if the game already has an opposing player.
			game = getGameForRetryToken(matchmakingRequest.retry.token);
		} else {
			// Try to find a game to join
			game = findGameAwaitingOpposingPlayer();

			// If there is no game to join, create a new one and set a retry request.
			if (game == null) {
				game = create();
			}

			// Create a player record
			Deck deck = matchmakingRequest.deck;
			Player thisPlayer = createPlayer(userId, deck);
			game.setNullPlayer(thisPlayer);
			save(game);
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
				response.retry = createRetry(game);
			} else {
				response.retry = matchmakingRequest.retry;
			}
		}

		// Assert that all the fields in the response are set
		return response;
	}

	private MatchmakingRequestRetry createRetry(Game game) {
		MatchmakingRequestRetry retry = new MatchmakingRequestRetry();
		// TODO: The retry token should be distinct from the game ID, or whatever method we use to matchmake in the future.
		retry.token = game.id;
		retry.delayMilliseconds = 3000;
		return retry;
	}

	private Player createPlayer(String userId, Deck deck) {
		Player thisPlayer = new Player();
		thisPlayer.userId = userId;
		thisPlayer.deck = deck;
		thisPlayer.profile = new Accounts().getProfileForId(userId);
		thisPlayer.channelId = createChannel(userId, ChannelType.SQS);
		return thisPlayer;
	}

	private String createChannel(String userId, ChannelType type) {
		AWSCredentials credentials = new Accounts().getAWSCredentials();
		AmazonSQSClient client = new AmazonSQSClient(credentials);
		String queueName = userId.concat(":").concat(UUID.randomUUID().toString());
		client.createQueue(queueName);
		return queueName;
	}

	public Game getGameCensored(String forUserId, Game game) {
		// TODO: For now, just return the game as is.
		return game;
	}

	public Game getGameForRetryToken(String retryToken) {
		// TODO: Assert that the user is authorized to actually retry for the given token
		// TODO: Look up the game.
		return new Game();
	}

	public Game get(String gameId) {
		return new Game();
	}

	public Game create() {
		Game game = new Game();
	}

	public void save(Game game) {

	}

	public Game findGameAwaitingOpposingPlayer() {
		// Query the database and return a game record without an opposing player

		return new Game();
	}
}
