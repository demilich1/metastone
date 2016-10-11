package com.hiddenswitch.proto3.net.behaviour;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.hiddenswitch.proto3.net.models.GameActionMessage;
import net.demilich.metastone.BuildConfig;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.behaviour.IBehaviour;
import net.demilich.metastone.game.cards.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RemotePlayer implements IBehaviour {
	private AmazonSQSAsyncClient sqsClient;
	private String gameQueueUrl;

	private void initializeNotifications() {
		AWSCredentials credentials = null;
		try {
			credentials = getAWSCredentials();
		} catch (Exception e) {
			throw new AmazonClientException("Failed to load valid credentials to connect to Amazon SQS.");
		}

		sqsClient = new AmazonSQSAsyncClient(credentials);
		sqsClient.setRegion(getAWSRegion());
	}

	private Region getAWSRegion() {
		return Region.getRegion(Regions.US_EAST_1);
	}

	private AWSCredentials getAWSCredentials() {
		return new ProfileCredentialsProvider("metastone_net").getCredentials();
	}

	@Override
	public IBehaviour clone() {
		return null;
	}

	@Override
	public String getName() {
		return "Remote Player";
	}

	/**
	 * In our game, there is no mulligan, so remote players do not mulligan.
	 *
	 * @param context
	 * @param player
	 * @param cards
	 * @return
	 */
	@Override
	public List<Card> mulligan(GameContext context, Player player, List<Card> cards) {
		return new ArrayList<>();
	}

	@Override
	public void onGameOver(GameContext context, int playerId, int winningPlayerId) {

	}


	/**
	 * For a remote player, wait until a networked notification service posts
	 * the remote player's action, and then process it here. Cheating is possible
	 * because the game logic is still being processed on each client, when you'd
	 * rather the context be delivered directly.
	 *
	 * @param context
	 * @param player
	 * @param validActions
	 * @return
	 */
	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
		Future<ReceiveMessageResult> receiveMessage = sqsClient.receiveMessageAsync(new ReceiveMessageRequest(gameQueueUrl));
		while (!receiveMessage.isDone()) {
			try {
				Thread.sleep(BuildConfig.DEFAULT_SLEEP_DELAY);
				if (context.ignoreEvents()) {
					return null;
				}
			} catch (InterruptedException e) {
				// TODO: Notify the game client that the game has disconnected.
			}
		}
		ReceiveMessageResult result = null;
		try {
			result = receiveMessage.get();
		} catch (InterruptedException e) {
			// TODO: If the request was interrupted, find out why.
			// Possible recourse: notify the game client that the game has disconnected.
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO: Find out why the execution of the request failed.
			e.printStackTrace();
		}

		// Process the message.
		List<Message> messages = result.getMessages();

		if (messages == null) {
			// TODO: What do we do if there are no messages, retry?
		}

		List<GameActionMessage> gameActionMessages = new ArrayList<>();

		for (Message message : messages) {

		}
		return null;
	}
}
