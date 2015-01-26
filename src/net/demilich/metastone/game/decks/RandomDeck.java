package net.demilich.metastone.game.decks;

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
		CardCollection catalogue = CardCatalogue.query(null, null, getHeroClass());
		catalogue.addAll(CardCatalogue.query(null, null, HeroClass.ANY));
		
		while (!copyDeck.isComplete()) {
			Card randomCard = catalogue.getRandom();
			if (deckValidator.canAddCardToDeck(randomCard, copyDeck)) {
				copyDeck.getCards().add(randomCard);
			}
		}
		return copyDeck.getCardsCopy();
	}

}
