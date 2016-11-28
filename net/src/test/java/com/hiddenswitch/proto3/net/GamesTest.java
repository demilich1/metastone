package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.models.MatchmakingRequest;
import com.hiddenswitch.proto3.net.models.MatchmakingResponse;
import com.hiddenswitch.proto3.net.util.TwoClients;
import io.vertx.core.Vertx;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class GamesTest extends ServiceTestBase<Games> {
	@BeforeMethod
	public void loadCards() {
		try {
			CardCatalogue.loadCardsFromPackage();
		} catch (IOException | URISyntaxException | CardParseException e) {
			Assert.fail("Did not load cards.", e);
		}
	}

	@Test
	public void testMatchmakeAndJoin() throws Exception {
		createTwoPlayersAndMatchmake();
	}

	private String createTwoPlayersAndMatchmake() throws InterruptedException {
		String player1 = "player1";
		String player2 = "player2";

		// Assume player 1's identity
		MatchmakingRequest request1 = new MatchmakingRequest();
		Deck deck1 = DeckFactory.getRandomDeck();
		request1.deck = deck1;
		request1.retry = null;
		MatchmakingResponse response1 = service.matchmakeAndJoin(request1, player1);
		assertNotNull(response1.getRetry());
		assertNull(response1.getConnection());
		assertNotNull(response1.getRetry().getGameId());
		assertNotNull(response1.getRetry().getReceipt());

		// Assume player 2's identity
		MatchmakingRequest request2 = new MatchmakingRequest();
		Deck deck2 = DeckFactory.getRandomDeck();
		request2.deck = deck2;
		request2.retry = null;
		MatchmakingResponse response2 = service.matchmakeAndJoin(request2, player2);
		assertNull(response2.getRetry());
		assertNotNull(response2.getConnection());

		// Assume player 1's identity, poll for matchmaking again and receive the new game information
		request1 = new MatchmakingRequest();
		request1.deck = DeckFactory.getRandomDeck();
		request1.retry = response1.getRetry();
		response1 = service.matchmakeAndJoin(request1, player1);
		assertNull(response1.getRetry());
		assertNotNull(response1.getConnection());

		// Now try connecting
		TwoClients twoClients = new TwoClients().invoke(response1, deck1, response2, deck2, response1.getConnection().getFirstMessage().getGameId(), service.getGameSessions());
		twoClients.play();
		float time = 0f;
		while (time < 40f && !twoClients.gameDecided()) {
			Thread.sleep(100);
			time += 0.1f;
		}
		twoClients.assertGameOver();
		return response1.getConnection().getFirstMessage().getGameId();
	}

	@Test
	public void testGetMatchmakingQueueUrl() throws Exception {
		assertNotNull(service.getMatchmakingQueueUrl());
	}

	@Override
	public Games setupAndReturnServiceInstance() {
		Vertx vertx = Vertx.vertx();
		GameSessions gameSessions = new GameSessions();
		vertx.deployVerticle(gameSessions);
		Games instance = new Games().withGameSessions(gameSessions);
		vertx.deployVerticle(instance);
		return instance;
	}
}