package net.demilich.metastone.game.decks;

import java.util.concurrent.ThreadLocalRandom;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.gui.deckbuilder.validation.DefaultDeckValidator;
import net.demilich.metastone.gui.deckbuilder.validation.IDeckValidator;

public class RandomDeck extends Deck {

	public RandomDeck(HeroClass heroClass) {
		super(heroClass);
		setName("[Random deck]");
	}

	@Override
	public CardCollection getCardsCopy() {
		Deck copyDeck = new Deck(getHeroClass());
		IDeckValidator deckValidator = new DefaultDeckValidator();
		CardCollection classCards = CardCatalogue.query(null, null, getHeroClass());
		CardCollection neutralCards = CardCatalogue.query(null, null, HeroClass.ANY);
		
		while (!copyDeck.isComplete()) {
			// random deck consists of rougly 50% class cards and 50% neutral cards
			Card randomCard = ThreadLocalRandom.current().nextBoolean() ? classCards.getRandom() : neutralCards.getRandom();
			if (deckValidator.canAddCardToDeck(randomCard, copyDeck)) {
				copyDeck.getCards().add(randomCard);
			}
		}
		return copyDeck.getCardsCopy();
	}

}
