package com.hiddenswitch.proto3.net;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.hiddenswitch.proto3.net.util.TwoClients;
import net.demilich.metastone.game.cards.CardParseException;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.DeckFormat;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class GameSessionsTest extends ServiceTestBase<GameSessions> {
    @Test
    public void testCreateGameSession() throws Exception {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.WARN);
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

    public DeckFormat getDeckFormat() {
        DeckFormat format = new DeckFormat();
        format.addSet(CardSet.PROCEDURAL_PREVIEW);
        return format;
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