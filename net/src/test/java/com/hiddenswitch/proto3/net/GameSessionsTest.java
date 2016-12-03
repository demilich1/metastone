package com.hiddenswitch.proto3.net;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.hiddenswitch.proto3.net.util.Result;
import com.hiddenswitch.proto3.net.util.TwoClients;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RunWith(VertxUnitRunner.class)
public class GameSessionsTest extends ServiceTestBase<GameSessions> {
	private org.slf4j.Logger logger = LoggerFactory.getLogger(GameSessionsTest.class);

	@Before
	public void setErrorLevel() {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);
	}

	@Before
	public void loadCards(TestContext context) {
		final Async async = context.async();
		try {
			CardCatalogue.loadCardsFromPackage();
		} catch (IOException | URISyntaxException | CardParseException e) {
			Assert.fail(e.getMessage());
		}
		async.complete();
	}

	@Test
	public void testCreateGameSession(TestContext context) throws CardParseException, IOException, URISyntaxException {
		wrapBlocking(context, this::getAndTestTwoClients);
	}

	private TwoClients getAndTestTwoClients() {
		TwoClients twoClients = null;
		try {
			twoClients = new TwoClients().invoke(this.service);
		} catch (IOException | URISyntaxException | CardParseException e) {
			ServiceTestBase.getContext().fail(e.getMessage());
		}
		try {
			twoClients.play();
			float seconds = 0.0f;
			while (seconds <= 40.0f && !twoClients.gameDecided()) {
				if (twoClients.isInterrupted()) {
					break;
				}
				Thread.sleep(1000);
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
		wrapBlocking(context, () -> {
			getAndTestTwoClients();
			getAndTestTwoClients();
		});

	}

	@Test
	public void testTwoSimultaneousSessions(TestContext context) throws Exception {
		wrapBlocking(context, () -> {
			simultaneousSessions(2);
		});
	}

	@Test(timeout = 20 * 60 * 1000L)
	public void testTenSessionsTenTimes(TestContext context) throws Exception {
		wrapBlocking(context, () -> {
			for (int i = 0; i < 10; i++) {
				simultaneousSessions(10);
				logger.info("Iteration completed : " + (i + 1));
			}
		});
	}


	private void simultaneousSessions(int sessions) {
		List<TwoClients> clients = new ArrayList<>();
		for (int i = 0; i < sessions; i++) {
			try {
				clients.add(new TwoClients().invoke(this.service));
			} catch (IOException | URISyntaxException | CardParseException e) {
				ServiceTestBase.getContext().fail(e.getMessage());
			}
		}

		try {
			clients.forEach(TwoClients::play);
			float seconds = 0.0f;
			while (seconds <= 240.0f && !clients.stream().allMatch(TwoClients::gameDecided)) {
				if (clients.stream().anyMatch(TwoClients::isInterrupted)) {
					break;
				}
				Thread.sleep(1000);
				seconds += 1.0f;
			}
			clients.forEach(TwoClients::assertGameOver);
		} catch (Exception e) {
			ServiceTestBase.getContext().fail(e.getMessage());
		} finally {
			clients.forEach(TwoClients::dispose);
		}
	}

	@Override
	public void deployServices(Vertx vertx, Handler<AsyncResult<GameSessions>> done) {
		GameSessions instance = new GameSessions();
		vertx.deployVerticle(instance, then -> {
			done.handle(new Result<>(instance));
		});
	}
}