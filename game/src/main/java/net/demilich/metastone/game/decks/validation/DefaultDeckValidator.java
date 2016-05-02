package net.demilich.metastone.game.decks.validation;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.logic.GameLogic;

public class DefaultDeckValidator implements IDeckValidator {

	@Override
	public boolean canAddCardToDeck(Card card, Deck deck) {
		if (deck.getCards().getCount() >= GameLogic.MAX_DECK_SIZE) {
			return false;
		}
		int cardInDeckCount = deck.containsHowMany(card);
		return card.getRarity() == Rarity.LEGENDARY ? cardInDeckCount < 1 : cardInDeckCount < 2;
	}

}
