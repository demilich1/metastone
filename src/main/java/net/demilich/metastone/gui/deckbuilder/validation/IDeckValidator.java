package net.demilich.metastone.gui.deckbuilder.validation;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.decks.Deck;

public interface IDeckValidator {

	boolean canAddCardToDeck(Card card, Deck deck);

}
