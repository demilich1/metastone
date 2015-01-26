package net.demilich.metastone.gui.common;

import javafx.util.StringConverter;
import net.demilich.metastone.game.decks.Deck;

public class DeckStringConverter extends StringConverter<Deck> {

	@Override
	public Deck fromString(String arg0) {
		return null;
	}

	@Override
	public String toString(Deck deck) {
		return deck.getName();
	}

}