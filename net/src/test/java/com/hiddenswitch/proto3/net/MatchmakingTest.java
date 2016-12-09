package com.hiddenswitch.proto3.net;

import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;
import co.paralleluniverse.strands.Strand;
import com.hiddenswitch.proto3.net.common.MatchmakingRequest;
import com.hiddenswitch.proto3.net.common.MatchmakingResponse;
import com.hiddenswitch.proto3.net.impl.GamesImpl;
import com.hiddenswitch.proto3.net.impl.MatchmakingImpl;
import com.hiddenswitch.proto3.net.models.MatchExpireRequest;
import com.hiddenswitch.proto3.net.util.Result;
import com.hiddenswitch.proto3.net.util.ServiceTestBase;
import com.hiddenswitch.proto3.net.util.TwoClients;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.sync.Sync;
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
public class MatchmakingTest extends ServiceTestBase<MatchmakingImpl> {
	private Logger logger = LoggerFactory.getLogger(MatchmakingTest.class);
	private GamesImpl gameSessions;

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
	public void testMatchmakeAndJoin(TestContext context) {
		wrapSync(context, this::createTwoPlayersAndMatchmake);
	}

	@Test
	public void testMatchmakeSamePlayersTwice(TestContext context) {
		wrapSync(context, () -> {
			// Creates the same two players
			String gameId = createTwoPlayersAndMatchmake();
			Strand.sleep(1000L);
			getContext().assertNull(gameSessions.getGameSession(gameId));
			final MatchExpireRequest request = new MatchExpireRequest(gameId);
			getContext().assertFalse(service.expireMatch(request).expired, "We should fail to expire an already expired match.");
			createTwoPlayersAndMatchmake();
		});

	}

	@Suspendable
	private String createTwoPlayersAndMatchmake() throws SuspendExecution, InterruptedException {
		logger.info("Starting matchmaking...");
		String player1 = "player1";
		String player2 = "player2";

		// Assume player 1's identity
		MatchmakingRequest request1 = new MatchmakingRequest();
		request1.userId = player1;
		Deck deck1 = DeckFactory.getRandomDeck();
		request1.deck = deck1;
		MatchmakingResponse response1 = null;

		response1 = service.matchmakeAndJoin(request1);
		getContext().assertNotNull(response1.getRetry());
		getContext().assertNull(response1.getConnection());
		getContext().assertNull(response1.getRetry().deck);
		logger.info("Matchmaking for player1 entered.");

		// Assume player 2's identity
		MatchmakingRequest request2 = new MatchmakingRequest();
		request2.userId = player2;
		Deck deck2 = DeckFactory.getRandomDeck();
		request2.deck = deck2;
		MatchmakingResponse response2 = null;

		response2 = service.matchmakeAndJoin(request2);
		getContext().assertNull(response2.getRetry());
		getContext().assertNotNull(response2.getConnection());
		logger.info("Matchmaking for player2 entered.");

		// Assume player 1's identity, poll for matchmaking again and receive the new game information
		request1 = response1.getRetry();

		response1 = service.matchmakeAndJoin(request1);
		getContext().assertNull(response1.getRetry());
		getContext().assertNotNull(response1.getConnection());
		logger.info("Matchmaking for player1 entered, 2nd time.");

		// Now try connecting
		TwoClients twoClients = new TwoClients().invoke(response1, deck1, response2, deck2, response1.getConnection().getFirstMessage().getGameId(), gameSessions);
		twoClients.play();
		float time = 0f;
		while (time < 60f && !twoClients.gameDecided()) {
			Strand.sleep(1000);
			time += 1.0f;
		}
		twoClients.assertGameOver();
		return response1.getConnection().getFirstMessage().getGameId();
	}

	@Override
	public void deployServices(Vertx vertx, Handler<AsyncResult<MatchmakingImpl>> done) {
		logger.info("Deploying services...");
		gameSessions = new GamesImpl();
		MatchmakingImpl instance = new MatchmakingImpl();
		vertx.deployVerticle(gameSessions, then -> {
			vertx.deployVerticle(instance, then2 -> {
				logger.info("Services deployed.");
				done.handle(new Result<>(instance));
			});
		});
	}
}