package net.pferdimanzug.hearthstone.analyzer.game.decks;

import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;

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
		return new RandomDeck(heroClass);
	}
	

}
