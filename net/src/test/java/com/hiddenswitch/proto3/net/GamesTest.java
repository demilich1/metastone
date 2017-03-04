package com.hiddenswitch.proto3.net;

import ch.qos.logback.classic.Level;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;
import co.paralleluniverse.strands.Strand;
import com.hiddenswitch.proto3.net.impl.GamesImpl;
import com.hiddenswitch.proto3.net.models.EndGameSessionRequest;
import com.hiddenswitch.proto3.net.util.Result;
import com.hiddenswitch.proto3.net.util.ServiceRuntime;
import com.hiddenswitch.proto3.net.util.TwoClients;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import net.demilich.metastone.game.cards.CardParseException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static net.demilich.metastone.game.GameContext.PLAYER_2;

@RunWith(VertxUnitRunner.class)
public class GamesTest extends ServiceRuntime<GamesImpl> {
	private Logger logger = LoggerFactory.getLogger(GamesTest.class);

	@Test
	public void testCreateGameSession(TestContext context) throws CardParseException, IOException, URISyntaxException {
		setLoggingLevel(Level.ERROR);
		wrapSync(context, this::getAndTestTwoClients);
	}

	private TwoClients getAndTestTwoClients() throws SuspendExecution {
		TwoClients twoClients = null;
		try {
			twoClients = new TwoClients().invoke(this.service);
		} catch (IOException | URISyntaxException | CardParseException e) {
			ServiceRuntime.getContext().fail(e.getMessage());
		}
		try {
			twoClients.play();
			float seconds = 0.0f;
			while (seconds <= 40.0f && !twoClients.gameDecided()) {
				if (twoClients.isInterrupted()) {
					break;
				}
				Strand.sleep(1000);
				seconds += 1.0f;
			}

			twoClients.assertGameOver();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		} finally {
			twoClients.dispose();
		}
		return twoClients;
	}

	@Test
	public void testTwoGameSessionsOneAfterAnother(TestContext context) throws CardParseException, IOException, URISyntaxException {
		setLoggingLevel(Level.ERROR);
		wrapSync(context, () -> {
			getAndTestTwoClients();
			getAndTestTwoClients();
		});
	}

	@Test(timeout = 20000L)
	public void testTerminatingSession(TestContext context) throws CardParseException, IOException, URISyntaxException {
		setLoggingLevel(Level.ERROR);
		wrapSync(context, () -> {
			try {
				TwoClients clients1 = new TwoClients().invoke(this.service);
				getContext().assertNotNull(clients1);
				clients1.play();
				logger.info("testTerminatingSession: Waiting for game to start...");
				while (clients1.getServerGameContext() == null) {
					Strand.sleep(100);
				}
				logger.info("testTerminatingSession: Waiting for game to reach turn 3...");
				while (clients1.getServerGameContext().getTurn() < 3) {
					Strand.sleep(100);
				}
				String gameId = clients1.getGameId();
				service.endGameSession(new EndGameSessionRequest(gameId));
				getContext().assertNull(service.getGameSession(gameId));
				logger.info("testTerminatingSession: Waiting for players to receive game end message...");
				while (!clients1.getPlayerContext1().gameDecided()
						&& !clients1.getPlayerContext2().gameDecided()) {
					Strand.sleep(100);
				}
				clients1.assertGameOver();
			} catch (Throwable e) {
				getContext().fail(e);
			}
		});
	}

	@Test(timeout = 40000L)
	public void testTimeoutSession(TestContext context) {
		setLoggingLevel(Level.ERROR);
		wrapSync(context, () -> {
			try {
				TwoClients clients1 = new TwoClients().invoke(this.service, 10000L);
				clients1.play();
				while (clients1.getServerGameContext() == null
						|| clients1.getServerGameContext().getTurn() < 3) {
					Strand.sleep(100);
				}
				String gameId = clients1.getGameId();
				clients1.disconnect(0);
				// This is greater than the timeout
				Strand.sleep(14000L);
				// From player 2's point of view, the game should be decided because it's over
				getContext().assertNull(service.getGameSession(gameId));
				getContext().assertTrue(clients1.getPlayerContext2().gameDecided());

			} catch (Throwable e) {
				getContext().fail(e);
			}
		});
	}

	@Test(timeout = 40000L)
	public void testRemoveSessionAfterNormalGameOver(TestContext context) {
		setLoggingLevel(Level.ERROR);
		wrapSync(context, () -> {
			try {
				TwoClients twoClients = getAndTestTwoClients();
				String gameId = twoClients.getGameId();
				// Exceeds the cleanup time
				Strand.sleep(1000L);
				getContext().assertNull(service.getGameSession(gameId));
			} catch (Throwable e) {
				getContext().fail(e);
			}
		});
	}

	@Test(timeout = 80000L)
	public void testTwoSimultaneousSessions(TestContext context) throws Exception {
		wrapSync(context, () -> {
			simultaneousSessions(2);
		});
	}

	@Test(timeout = 45 * 60 * 1000L)
	public void testTenSimultaneousSessionsTwice(TestContext context) throws Exception {
		setLoggingLevel(Level.ERROR);
		wrapSync(context, () -> {
			for (int i = 0; i < 2; i++) {
				simultaneousSessions(10);
				logger.info("Iteration completed : " + (i + 1));
			}
		});
	}

	@Test(timeout = 80000L)
	public void testReconnects(TestContext context) throws Exception {
		setLoggingLevel(Level.ERROR);
		wrapSync(context, () -> {
			TwoClients twoClients = null;
			try {
				twoClients = new TwoClients().invoke(this.service);
				twoClients.play();
				while (twoClients.getServerGameContext() == null
						|| twoClients.getServerGameContext().getTurn() < 3) {
					Strand.sleep(100);
				}
				twoClients.disconnect(PLAYER_2);
				Strand.sleep(1000);
				// Try to reconnect
				twoClients.connect(PLAYER_2);
				twoClients.play(PLAYER_2);

				float seconds = 0.0f;
				while (seconds <= 40.0f && !twoClients.gameDecided()) {
					if (twoClients.isInterrupted()) {
						break;
					}
					Strand.sleep(1000);
					seconds += 1.0f;
				}

				twoClients.assertGameOver();
			} catch (Exception e) {
				getContext().fail(e);
			} finally {
				if (twoClients != null) {
					twoClients.dispose();
				}
			}
		});
	}


	@Suspendable
	private void simultaneousSessions(int sessions) throws SuspendExecution {
		List<TwoClients> clients = new ArrayList<>();
		for (int i = 0; i < sessions; i++) {
			try {
				clients.add(new TwoClients().invoke(this.service));
			} catch (IOException | URISyntaxException | CardParseException e) {
				ServiceRuntime.getContext().fail(e.getMessage());
			}
		}

		clients.forEach(TwoClients::play);
		float seconds = 0.0f;
		while (seconds <= 600.0f && !clients.stream().allMatch(TwoClients::gameDecided)) {
			if (clients.stream().anyMatch(TwoClients::isInterrupted)) {
				break;
			}
			clients.forEach(c -> {
				final long c1 = c.getPlayerContext1().clientDelay();
				final long c2 = c.getPlayerContext2().clientDelay();
				final boolean gd1 = c.getPlayerContext1().gameDecided();
				final boolean gd2 = c.getPlayerContext2().gameDecided();
				if ((!gd1 && c1 > 20e9) || (!gd2 && c2 > 20e9)) {
					logger.info(String.format("Delayed game %s thread: %s", c.getGameId(), c.getPlayerContext1().isActivePlayer() ? c.getThread1().getName() : c.getThread2().getName()));
				}
			});
			if (clients.stream().anyMatch(c -> c.isTimedOut((long) 40e9))) {
				break;
			}
			try {
				Strand.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			seconds += 1.0f;
		}
		clients.forEach(TwoClients::assertGameOver);
	}

	@Override
	public void deployServices(Vertx vertx, Handler<AsyncResult<GamesImpl>> done) {
		setLoggingLevel(Level.ERROR);
		GamesImpl instance = new GamesImpl();
		vertx.deployVerticle(instance, then -> {
			done.handle(new Result<>(instance));
		});
	}
}
