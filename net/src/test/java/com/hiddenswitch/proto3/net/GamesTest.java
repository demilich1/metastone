package com.hiddenswitch.proto3.net;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.hiddenswitch.proto3.net.models.CreateAccountResponse;
import com.hiddenswitch.proto3.net.models.MatchmakingRequest;
import com.hiddenswitch.proto3.net.models.MatchmakingResponse;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import org.testng.annotations.Test;
import com.hiddenswitch.proto3.net.*;

import static org.testng.Assert.*;

public class GamesTest extends ServiceTestBase<Games> {
	@Test
	public void testMatchmakeAndJoin() throws Exception {
		CreateAccountResponse player1 = service.getAccounts().createAccount("aa@bb.com", "player1", "password");
		CreateAccountResponse player2 = service.getAccounts().createAccount("aa@bb.com", "player2", "password");

		// Assume player 1's identity
		service.getAccounts().setUserId(player1.userId);
		MatchmakingRequest request1 = new MatchmakingRequest();
		request1.deck = new Deck(HeroClass.MAGE);
		request1.retry = null;
		MatchmakingResponse response1 = service.matchmakeAndJoin(request1);
		assertNotNull(response1.retry);
		assertNull(response1.game);
		assertNull(response1.myChannelId);
		assertNotNull(response1.retry.getGameId());
		assertNotNull(response1.retry.getReceipt());

		// Assume player 2's identity
		service.getAccounts().setUserId(player2.userId);
		MatchmakingRequest request2 = new MatchmakingRequest();
		request2.deck = new Deck(HeroClass.WARRIOR);
		request2.retry = null;
		MatchmakingResponse response2 = service.matchmakeAndJoin(request2);
		assertNull(response2.retry);
		assertNotNull(response2.game);
		assertNotNull(response2.myChannelId);

		// Assume player 1's identity, poll for matchmaking again and receive the new game information
		service.getAccounts().setUserId(player1.userId);
		request1 = new MatchmakingRequest();
		request1.deck = new Deck(HeroClass.MAGE);
		request1.retry = response1.retry;
		response1 = service.matchmakeAndJoin(request1);
		assertNull(response1.retry);
		assertNotNull(response1.game);
		assertNotNull(response1.myChannelId);
	}

	@Test
	public void testDispose() throws Exception {

	}

	@Test
	public void testGetMatchmakingQueueUrl() throws Exception {

	}

	@Override
	public Games getServiceInstance() {
		Accounts accounts = new Accounts();
		Games games = new Games() {
			@Override
			public void setCredentials(AWSCredentials credentials) {
				super.setCredentials(credentials);
				getAccounts().setCredentials(credentials);
			}

			@Override
			public void setDatabase(DynamoDBMapper database) {
				super.setDatabase(database);
				getAccounts().setDatabase(database);
			}

			@Override
			public void setQueue(AmazonSQSClient queue) {
				super.setQueue(queue);
				getAccounts().setQueue(queue);
			}
		};
		games.setAccounts(accounts);
		return games;
	}
}