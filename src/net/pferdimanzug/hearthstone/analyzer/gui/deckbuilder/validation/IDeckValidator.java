package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.validation;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;

public interface IDeckValidator {
	
	boolean canAddCardToDeck(Card card, Deck deck);

}
