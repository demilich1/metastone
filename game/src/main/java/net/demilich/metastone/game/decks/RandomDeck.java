package net.demilich.metastone.game.decks;

import java.util.concurrent.ThreadLocalRandom;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.decks.validation.DefaultDeckValidator;
import net.demilich.metastone.game.decks.validation.IDeckValidator;

public class RandomDeck extends Deck {
	private DeckFormat deckFormat;

	public RandomDeck(HeroClass heroClass, DeckFormat deckFormat) {
		super(heroClass);
		this.deckFormat = deckFormat;
		IDeckValidator deckValidator = new DefaultDeckValidator();
		CardCollection classCards = CardCatalogue.query(deckFormat, card -> {
			return card.isCollectible()
					&& !card.getCardType().isCardType(CardType.HERO)
					&& !card.getCardType().isCardType(CardType.HERO_POWER)
					&& card.hasHeroClass(getHeroClass());
		});
		CardCollection neutralCards = CardCatalogue.query(deckFormat, card -> {
			return card.isCollectible()
					&& !card.getCardType().isCardType(CardType.HERO)
					&& !card.getCardType().isCardType(CardType.HERO_POWER)
					&& card.hasHeroClass(HeroClass.ANY);
		});

		while (!this.isComplete()) {
			// random deck consists of roughly 50% class cards and 50% neutral
			// cards

			Card randomCard = ThreadLocalRandom.current().nextBoolean() ? classCards.getRandom() : neutralCards.getRandom();
			if (deckValidator.canAddCardToDeck(randomCard, this)) {
				this.getCards().add(randomCard);
			}
		}

		setName("[Random deck]");
	}
}
