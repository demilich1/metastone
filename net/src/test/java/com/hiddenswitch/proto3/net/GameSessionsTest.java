package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.client.RemoteGameContext;
import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.common.NullNotifier;
import com.hiddenswitch.proto3.net.models.CreateGameSessionRequest;
import com.hiddenswitch.proto3.net.models.CreateGameSessionResponse;
import com.hiddenswitch.proto3.net.util.AI;
import com.hiddenswitch.proto3.net.util.AIPlayer;
import com.hiddenswitch.proto3.server.PregamePlayerConfiguration;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.DeckCatalogue;
import net.demilich.metastone.game.decks.DeckFactory;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.logic.GameLogic;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;

public class GameSessionsTest extends ServiceTestBase<GameSessions> {
	@Test
	public void testCreateGameSession() throws Exception {
//		NotificationProxy.init(new NullNotifier());
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.DEBUG);
		// TODO: Test reconnects
		CardCatalogue.loadCardsFromPackage();

		CreateGameSessionRequest request = new CreateGameSessionRequest();
		AtomicInteger atomicInteger = new AtomicInteger(0);
		AIPlayer player1 = new AIPlayer(atomicInteger);
		AIPlayer player2 = new AIPlayer(atomicInteger);
		PregamePlayerConfiguration pregame1 = new PregamePlayerConfiguration(player1.getConfiguredDeck(), "Player 1");
		pregame1.setPlayer(player1);
		PregamePlayerConfiguration pregame2 = new PregamePlayerConfiguration(player2.getConfiguredDeck(), "Player 2");
		pregame2.setPlayer(player2);

		request.setPregame1(pregame1);
		request.setPregame2(pregame2);

		CreateGameSessionResponse response = service.createGameSession(request);

		// Manually override the player in the configurations
		RemoteGameContext playerContext1 = createRemoteGameContext(response.getConfigurationForPlayer1());
		RemoteGameContext playerContext2 = createRemoteGameContext(response.getConfigurationForPlayer2());
		playerContext1.ignoreEventOverride = true;
		playerContext2.ignoreEventOverride = true;
		Thread thread1 = new Thread(playerContext1::play);
		Thread thread2 = new Thread(playerContext2::play);
		System.out.println(atomicInteger.get());

		try {
			thread1.start();
			thread2.start();

			float seconds = 0.0f;
			while (seconds <= 20.0f && atomicInteger.get() < 2) {
				if (thread1.isInterrupted() || thread2.isInterrupted()) {
					break;
				}
				Thread.sleep(100);
				seconds += 0.1f;
			}
			System.out.println(atomicInteger.get());

			Assert.assertEquals(atomicInteger.get(), 2);
		} catch (Exception e) {
			Assert.fail("Exception in execution", e);
		} finally {
			thread1.interrupt();
			thread2.interrupt();
		}
	}

	public DeckFormat getDeckFormat() {
		DeckFormat format = new DeckFormat();
		format.addSet(CardSet.PROCEDURAL_PREVIEW);
		return format;
	}

	private RemoteGameContext createRemoteGameContext(ClientConnectionConfiguration configuration) {
		try {
			DeckCatalogue.loadDecksFromPackage();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		RemoteGameContext context = new RemoteGameContext(configuration);

		return context;
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