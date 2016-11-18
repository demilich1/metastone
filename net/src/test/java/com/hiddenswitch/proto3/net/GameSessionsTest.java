package com.hiddenswitch.proto3.net;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.hiddenswitch.proto3.net.util.TwoClients;
import net.demilich.metastone.game.cards.CardParseException;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.DeckFormat;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scala.Array;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class GameSessionsTest extends ServiceTestBase<GameSessions> {
    @BeforeMethod
    @Override
    public void setUp() throws Exception {
        super.setUp();
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.WARN);
    }

    @Test
    public void testCreateGameSession() throws Exception {
        // TODO: Test reconnects
        TwoClients twoClients = new TwoClients().invoke(getServiceInstance());
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
    }

    @Test
    public void testMultipleSimultaneousSessions() throws Exception {
        List<TwoClients> clients = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            clients.add(new TwoClients().invoke(getServiceInstance()));
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
            if (service == null) {
                service = new GameSessions();
            }
            return service;
        } catch (IOException | URISyntaxException | CardParseException e) {
            e.printStackTrace();
            Assert.fail();
            return null;
        }
    }

}