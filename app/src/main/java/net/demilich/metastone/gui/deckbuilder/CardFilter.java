package net.demilich.metastone.gui.deckbuilder;

import net.demilich.metastone.game.cards.CardSet;

public class CardFilter {

	private final String text;
	private final CardSet set;

	public CardFilter(String text, CardSet set) {
		this.text = text;
		this.set = set;
	}

	public CardSet getSet() {
		return set;
	}

	public String getText() {
		return text;
	}

}
