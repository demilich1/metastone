package net.demilich.metastone.gui.deckbuilder;

import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.DeckFormat;

public class CardFilter {

	private final String text;
	private final CardSet set;
	private final DeckFormat format;

	public CardFilter(String text, CardSet set, DeckFormat format) {
		this.text = text;
		this.set = set;
		this.format = format;
	}

	public DeckFormat getFormat() {
		return format;
	}

	public CardSet getSet() {
		return set;
	}

	public String getText() {
		return text;
	}

}
