package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.importer;

import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;

public interface IDeckImporter {
	
	Deck importFrom(String uri);

}
