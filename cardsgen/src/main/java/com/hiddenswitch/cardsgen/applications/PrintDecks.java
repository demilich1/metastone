package com.hiddenswitch.cardsgen.applications;

import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckCatalogue;

import java.io.IOException;
import java.net.URISyntaxException;

public class PrintDecks {
	public static void main(String[] args) throws IOException, URISyntaxException {
		DeckCatalogue.loadDecks();

		for (Deck deck : DeckCatalogue.getDecks()) {
			System.out.println(deck.getName());
		}
	}
}
