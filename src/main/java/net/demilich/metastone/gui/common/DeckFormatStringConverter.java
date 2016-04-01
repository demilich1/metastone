package net.demilich.metastone.gui.common;

import javafx.util.StringConverter;
import net.demilich.metastone.game.decks.DeckFormat;

public class DeckFormatStringConverter extends StringConverter<DeckFormat> {

	@Override
	public DeckFormat fromString(String arg0) {
		return null;
	}

	@Override
	public String toString(DeckFormat format) {
		return format.getName();
	}

}