package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.validation;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;

public class DefaultDeckValidator implements IDeckValidator {

	@Override
	public boolean canAddCardToDeck(Card card, Deck deck) {
		if (deck.getCards().getCount() >= GameLogic.DECK_SIZE) {
			return false;
		}
		int cardInDeckCount = deck.containsHowMany(card);
		return card.getRarity() == Rarity.LEGENDARY ? cardInDeckCount < 1 : cardInDeckCount < 2;
	}

}
