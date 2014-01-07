package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCatalogue;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;

public class DebugDecks {
	
	public static CardCollection<Card> getRandomDeck(HeroClass heroClass) {
		CardCollection<Card> deck = new CardCollection<Card>();
		CardCollection<Card> classCards = CardCatalogue.query(null, Rarity.FREE, heroClass);
		for (Card card : classCards) {
			deck.add(card.clone());
			deck.add(card.clone());
		}
		CardCollection<Card> neutralCards = CardCatalogue.query(null, Rarity.FREE, HeroClass.ANY);
		while (deck.getCount() < 30) {
			Card randomCard = neutralCards.getRandom();
			neutralCards.remove(randomCard);
			deck.add(randomCard.clone());
			deck.add(randomCard.clone());
		}
		
		return deck;
	}
	

}
