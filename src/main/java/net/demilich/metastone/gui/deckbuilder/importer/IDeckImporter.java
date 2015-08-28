package net.demilich.metastone.gui.deckbuilder.importer;

import net.demilich.metastone.game.decks.Deck;

public interface IDeckImporter {

	Deck importFrom(String uri);

}
