package net.demilich.metastone.game.decks.validation;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.logic.GameLogic;

public class ArbitraryDeckValidator implements IDeckValidator {

	@Override
	public boolean canAddCardToDeck(Card card, Deck deck) {
		return deck.getCards().getCount() <= GameLogic.MAX_DECK_SIZE;
	}

}
