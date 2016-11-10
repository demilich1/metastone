package com.hiddenswitch.proto3.net;

import net.demilich.metastone.game.cards.CardParseException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.testng.Assert.*;

public class GameSessionsTest extends ServiceTestBase<GameSessions> {
	@Test
	public void testCreateGameSession() throws Exception {
		// TODO: Test reconnects

	}

	@Override
	public GameSessions getServiceInstance() {
		try {
			return new GameSessions();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (CardParseException e) {
			e.printStackTrace();
		}
	}
}