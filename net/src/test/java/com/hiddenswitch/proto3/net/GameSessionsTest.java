package com.hiddenswitch.proto3.net;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.hiddenswitch.proto3.net.util.TwoClients;
import net.demilich.metastone.game.cards.CardParseException;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class GameSessionsTest extends ServiceTestBase<GameSessions> {
	private org.slf4j.Logger logger = LoggerFactory.getLogger(GameSessionsTest.class);

	@BeforeMethod
	@Override
	public void setUp() throws Exception {
		super.setUp();
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.WARN);
	}

	@Test
	public void testCreateGameSession() throws CardParseException, IOException, URISyntaxException {
		getAndTestTwoClients();
	}

	private TwoClients getAndTestTwoClients() throws CardParseException, IOException, URISyntaxException {
		TwoClients twoClients = new TwoClients().invoke(this.service);
		try {
			twoClients.play();
			float seconds = 0.0f;
			while (seconds <= 20.0f && !twoClients.gameDecided()) {
				if (twoClients.isInterrupted()) {
					break;
				}
				Thread.sleep(100);
				seconds += 0.1f;
			}

			twoClients.assertGameOver();
		} catch (Exception e) {
			Assert.fail("Exception in execution", e);
		} finally {
			twoClients.dispose();
		}
		return twoClients;
	}

	@Test
	public void testTwoGameSessionsOneAfterAnother() throws CardParseException, IOException, URISyntaxException {
		getAndTestTwoClients();
		getAndTestTwoClients();
	}

	@Test
	public void testOldSessionDisposed() throws CardParseException, IOException, URISyntaxException {
		getAndTestTwoClients();
		getAndTestTwoClients();
		// The game server should have cleaned up the games by now.
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(this.service.getServer().getGames().size(), 0, "After four seconds, the server should have cleaned up any lingering games.");
	}

	@Test
	public void testTwoSimultaneousSessions() throws Exception {
		List<TwoClients> clients = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			clients.add(new TwoClients().invoke(this.service));
		}

		try {
			clients.forEach(TwoClients::play);
			float seconds = 0.0f;
			while (seconds <= 20.0f && !clients.stream().allMatch(TwoClients::gameDecided)) {
				if (clients.stream().anyMatch(TwoClients::isInterrupted)) {
					break;
				}
				Thread.sleep(100);
				seconds += 0.1f;
			}
			clients.forEach(TwoClients::assertGameOver);
		} catch (Exception e) {
			Assert.fail("Exception in execution", e);
		} finally {
			clients.forEach(TwoClients::dispose);
		}
	}

	@Override
	public GameSessions getServiceInstance() {
		try {
			return new GameSessions();
		} catch (IOException | URISyntaxException | CardParseException e) {
			e.printStackTrace();
			Assert.fail();
			return null;
		}
	}

}