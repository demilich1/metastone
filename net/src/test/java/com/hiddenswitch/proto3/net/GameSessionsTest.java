package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.client.RemoteGameContext;
import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.models.CreateGameSessionRequest;
import com.hiddenswitch.proto3.net.models.CreateGameSessionResponse;
import com.hiddenswitch.proto3.server.PregamePlayerConfiguration;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.DeckCatalogue;
import net.demilich.metastone.game.decks.DeckFactory;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.testng.Assert.*;

public class GameSessionsTest extends ServiceTestBase<GameSessions> {
	@Test
	public void testCreateGameSession() throws Exception {
		// TODO: Test reconnects
		CardCatalogue.loadCardsFromPackage();
		DeckFormat format = new DeckFormat();
		format.addSet(CardSet.PROCEDURAL_PREVIEW);

		CreateGameSessionRequest request = new CreateGameSessionRequest();
		PregamePlayerConfiguration player1 = new PregamePlayerConfiguration(DeckFactory.getRandomDeck(HeroClass.MAGE, format), "Player 1");
		PregamePlayerConfiguration player2 = new PregamePlayerConfiguration(DeckFactory.getRandomDeck(HeroClass.WARRIOR, format), "Player 2");

		request.setPlayer1(player1);
		request.setPlayer2(player2);

		CreateGameSessionResponse response = service.createGameSession(request);
	}

	private RemoteGameContext createRemoteGameContext(ClientConnectionConfiguration configuration) {

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