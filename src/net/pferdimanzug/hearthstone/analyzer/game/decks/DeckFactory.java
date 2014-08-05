package net.pferdimanzug.hearthstone.analyzer.game.decks;

import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCatalogue;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.validation.DefaultDeckValidator;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.validation.IDeckValidator;

public class DeckFactory {
	
	public static Deck getDeckConsistingof(int count, Card... cards) {
		CardCollection cardCollection = new CardCollection();
		for (int i = 0; i < count; i++) {
			int randomIndex = ThreadLocalRandom.current().nextInt(cards.length);
			cardCollection.add(cards[randomIndex].clone());
		}
		Deck deck = new Deck(HeroClass.ANY);
		deck.setName("[Debug deck]");
		deck.getCards().addAll(cardCollection);
		return deck;
	}
	
	public static Deck getRandomDeck(HeroClass heroClass) {
		Deck deck = new Deck(heroClass);
		deck.setName("[Random deck]");
		IDeckValidator deckValidator = new DefaultDeckValidator();
		CardCollection catalogue = CardCatalogue.query(null, null, heroClass);
		while(!deck.isComplete()) {
			Card randomCard = catalogue.getRandom();
			if (deckValidator.canAddCardToDeck(randomCard, deck)) {
				deck.getCards().add(randomCard.clone());
			}
		}
		return deck;
	}
	

}
