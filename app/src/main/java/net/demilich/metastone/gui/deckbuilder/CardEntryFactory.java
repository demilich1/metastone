package net.demilich.metastone.gui.deckbuilder;

import java.util.ArrayList;
import java.util.List;

import net.demilich.metastone.game.cards.Card;

public class CardEntryFactory {

	private static final int CARD_ENTRIES = 10;

	private List<CardEntry> cachedCardEntries = new ArrayList<CardEntry>(CARD_ENTRIES);

	public CardEntryFactory() {
		for (int i = 0; i < CARD_ENTRIES; i++) {
			cachedCardEntries.add(new CardEntry());
		}
	}

	public CardEntry createCardEntry(Card card) {
		CardEntry cardEntry = getCardEntry();
		cardEntry.resetStackCount();
		cardEntry.addCard(card);
		return cardEntry;
	}

	private CardEntry getCardEntry() {
		for (CardEntry handCard : cachedCardEntries) {
			if (handCard.getParent() == null) {
				return handCard;
			}
		}
		return new CardEntry();
	}

}
