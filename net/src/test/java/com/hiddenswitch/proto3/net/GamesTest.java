package com.hiddenswitch.proto3.net;

import co.paralleluniverse.strands.Strand;
import com.hiddenswitch.proto3.net.common.MatchmakingRequest;
import com.hiddenswitch.proto3.net.common.MatchmakingResponse;
import com.hiddenswitch.proto3.net.impl.GameSessionsImpl;
import com.hiddenswitch.proto3.net.impl.GamesImpl;
import com.hiddenswitch.proto3.net.models.MatchExpireRequest;
import com.hiddenswitch.proto3.net.util.Result;
import com.hiddenswitch.proto3.net.util.ServiceTestBase;
import com.hiddenswitch.proto3.net.util.TwoClients;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(VertxUnitRunner.class)
public class GamesTest extends ServiceTestBase<GamesImpl> {
	Logger logger = LoggerFactory.getLogger(GamesTest.class);

	@Before
	public void loadCards(TestContext context) {
		try {
			CardCatalogue.loadCardsFromPackage();
		} catch (IOException | URISyntaxException | CardParseException e) {
			context.fail(e);
		}
		context.async().complete();
	}

	@Test
	public void testMatchmakeAndJoin() {
		createTwoPlayersAndMatchmake();
	}

	@Test
	public void testMatchmakeSamePlayersTwice(TestContext context) {
		wrapBlocking(context, () -> {
			try {
				// Creates the same two players
				String gameId = createTwoPlayersAndMatchmake();
				Strand.sleep(1000L);
				getContext().assertNull(service.getGameSessions().getGameSession(gameId));
				final MatchExpireRequest request = new MatchExpireRequest();
				request.gameId = gameId;
				getContext().assertFalse(service.expireMatch(request).expired, "We should fail to expire an already expired match.");
				createTwoPlayersAndMatchmake();
			} catch (Throwable e) {
				getContext().fail(e);
			}
		});

	}

	private String createTwoPlayersAndMatchmake() {
		logger.info("Starting matchmaking...");
		String player1 = "player1";
		String player2 = "player2";

		// Assume player 1's identity
		MatchmakingRequest request1 = new MatchmakingRequest();
		Deck deck1 = DeckFactory.getRandomDeck();
		request1.deck = deck1;
		MatchmakingResponse response1 = service.matchmakeAndJoin(request1);
		assertNotNull(response1.getRetry());
		assertNull(response1.getConnection());
		assertNull(response1.getRetry().deck);
		logger.info("Matchmaking for player1 entered.");

		// Assume player 2's identity
		MatchmakingRequest request2 = new MatchmakingRequest();
		Deck deck2 = DeckFactory.getRandomDeck();
		request2.deck = deck2;
		MatchmakingResponse response2 = service.matchmakeAndJoin(request2);
		assertNull(response2.getRetry());
		assertNotNull(response2.getConnection());
		logger.info("Matchmaking for player2 entered.");

		// Assume player 1's identity, poll for matchmaking again and receive the new game information
		request1 = response1.getRetry();
		response1 = service.matchmakeAndJoin(request1);
		assertNull(response1.getRetry());
		assertNotNull(response1.getConnection());
		logger.info("Matchmaking for player1 entered, 2nd time.");

		// Now try connecting
		TwoClients twoClients = new TwoClients().invoke(response1, deck1, response2, deck2, response1.getConnection().getFirstMessage().getGameId(), service.getGameSessions());
		twoClients.play();
		float time = 0f;
		while (time < 40f && !twoClients.gameDecided()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Assert.fail(e.getMessage());
			}
			time += 0.1f;
		}
		twoClients.assertGameOver();
		return response1.getConnection().getFirstMessage().getGameId();
	}

	@Override
	public void deployServices(Vertx vertx, Handler<AsyncResult<GamesImpl>> done) {
		logger.info("Deploying services...");
		GameSessionsImpl gameSessions = new GameSessionsImpl();
		GamesImpl instance = new GamesImpl().withGameSessions(gameSessions);
		vertx.deployVerticle(gameSessions, then -> {
			vertx.deployVerticle(instance, then2 -> {
				logger.info("Services deployed.");
				done.handle(new Result<>(instance));
			});
		});
	}
}