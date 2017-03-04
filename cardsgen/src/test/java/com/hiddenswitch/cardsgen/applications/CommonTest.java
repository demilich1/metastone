package com.hiddenswitch.cardsgen.applications;

import net.demilich.metastone.game.decks.DeckCatalogue;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CommonTest {
	@Test
	public void testGetDefaultDecks() throws Exception {
		assertTrue(Common.getDefaultDecks().size() > 0);
	}

	@Test
	public void testAllDecksValid() throws Exception {
		DeckCatalogue.loadDecksFromPackage();
		Common.getDefaultDecks().forEach(d -> {
			assertNotNull(DeckCatalogue.getDeckByName(d));
		});
	}
}