package com.hiddenswitch.proto3.net;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.hiddenswitch.proto3.net.models.CreateAccountResponse;
import com.hiddenswitch.proto3.net.models.Game;
import com.hiddenswitch.proto3.net.models.MatchmakingRequest;
import com.hiddenswitch.proto3.net.models.MatchmakingResponse;
import net.demilich.metastone.game.decks.Bench;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class GamesTest extends ServiceTestBase<Games> {
	@Test
	public void testMatchmakeAndJoin() throws Exception {
		createTwoPlayersAndMatchmake();
	}

	private String createTwoPlayersAndMatchmake() {
		CreateAccountResponse player1 = service.getAccounts().createAccount("aa@bb.com", "player1", "password");
		CreateAccountResponse player2 = service.getAccounts().createAccount("aa@bb.com", "player2", "password");

		// Assume player 1's identity
		service.getAccounts().setUserId(player1.userId);
		MatchmakingRequest request1 = new MatchmakingRequest();
		request1.deck = new Bench(HeroClass.MAGE);
		request1.retry = null;
		MatchmakingResponse response1 = service.matchmakeAndJoin(request1);
		assertNotNull(response1.getRetry());
		assertNull(response1.getConnection());
		assertNotNull(response1.getRetry().getGameId());
		assertNotNull(response1.getRetry().getReceipt());

		// Assume player 2's identity
		service.getAccounts().setUserId(player2.userId);
		MatchmakingRequest request2 = new MatchmakingRequest();
		request2.deck = new Bench(HeroClass.WARRIOR);
		request2.retry = null;
		MatchmakingResponse response2 = service.matchmakeAndJoin(request2);
		assertNull(response2.getRetry());
		assertNotNull(response2.getConnection());

		// Assume player 1's identity, poll for matchmaking again and receive the new game information
		service.getAccounts().setUserId(player1.userId);
		request1 = new MatchmakingRequest();
		request1.deck = new Bench(HeroClass.MAGE);
		request1.retry = response1.getRetry();
		response1 = service.matchmakeAndJoin(request1);
		assertNull(response1.getRetry());
		assertNotNull(response1.getConnection());

		return response1.getConnection().getFirstMessage().getGameId();
	}

	@Test
	public void testDispose() throws Exception {
		String gameId = createTwoPlayersAndMatchmake();
		// Find the game
		Game game = service.get(gameId);
		service.dispose(gameId);
	}

	@Test
	public void testGetMatchmakingQueueUrl() throws Exception {
		assertNotNull(service.getMatchmakingQueueUrl());
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