package com.hiddenswitch.proto3.net.util;

import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;
import co.paralleluniverse.strands.Strand;
import com.hiddenswitch.proto3.net.MatchmakingTest;
import com.hiddenswitch.proto3.net.common.MatchmakingRequest;
import com.hiddenswitch.proto3.net.common.MatchmakingResponse;
import com.hiddenswitch.proto3.net.impl.BotsImpl;
import com.hiddenswitch.proto3.net.impl.GamesImpl;
import com.hiddenswitch.proto3.net.impl.MatchmakingImpl;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFactory;

import static net.demilich.metastone.game.GameContext.PLAYER_1;
import static net.demilich.metastone.game.GameContext.PLAYER_2;

/**
 * Created by bberman on 12/16/16.
 */
public abstract class AbstractMatchmakingRuntime extends ServiceRuntime<MatchmakingImpl> {
	protected GamesImpl gameSessions;
	private Logger logger = LoggerFactory.getLogger(MatchmakingTest.class);
	private BotsImpl bots;

	@Suspendable
	protected String createTwoPlayersAndMatchmake() throws SuspendExecution, InterruptedException {
		logger.info("Starting matchmaking...");
		String player1 = "player1";
		String player2 = "player2";

		// Assume player 1's identity
		MatchmakingRequest request1 = new MatchmakingRequest();
		request1.userId = player1;
		Deck deck1 = createDeckForMatchmaking(PLAYER_1);
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
		Deck deck2 = createDeckForMatchmaking(PLAYER_2);
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

	protected Deck createDeckForMatchmaking(int playerId) {
		return DeckFactory.getRandomDeck();
	}

	@Override
	public void deployServices(Vertx vertx, Handler<AsyncResult<MatchmakingImpl>> done) {
		logger.info("Deploying services...");
		gameSessions = new GamesImpl();
		bots = new BotsImpl();
		MatchmakingImpl instance = new MatchmakingImpl();
		vertx.deployVerticle(gameSessions, then -> {
			vertx.deployVerticle(bots, then2 -> {
				vertx.deployVerticle(instance, then3 -> {
					logger.info("Services deployed.");
					done.handle(new Result<>(instance));
				});
			});
		});
	}
}
