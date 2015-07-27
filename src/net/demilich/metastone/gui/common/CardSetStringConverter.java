package net.demilich.metastone.gui.common;

import javafx.util.StringConverter;
import net.demilich.metastone.game.cards.CardSet;

public class CardSetStringConverter extends StringConverter<CardSet> {

	@Override
	public String toString(CardSet object) {
		return object.toString();
	}

	@Override
	public CardSet fromString(String string) {
		return null;
	}
}
