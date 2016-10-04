package net.demilich.metastone.game.decks;

import java.util.ArrayList;
import java.util.List;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardSet;

public class DeckFormat {

	private String name = "";
	private String filename;
	private List<CardSet> sets;

	public DeckFormat() {
		sets = new ArrayList<CardSet>();
	}

	public void addSet(CardSet cardSet) {
		sets.add(cardSet);
	}

	public boolean isInFormat(Card card) {
		if (sets.contains(card.getCardSet())) {
			return true;
		}
		return false;
	}

	public boolean isInFormat(CardSet set) {
		return sets.contains(set);
	}

	public boolean isInFormat(Deck deck) {
		for (Card card : deck.getCards()) {
			if (!isInFormat(card)) {
				return false;
			}
		}
		return true;
	}

	public List<CardSet> getCardSets() {
		return new ArrayList<CardSet>(sets);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
