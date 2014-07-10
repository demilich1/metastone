package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCatalogue;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;

public class DebugDecks {
	
	public static CardCollection getDeckConsistingof(int count, Card... cards) {
		CardCollection deck = new CardCollection();
		for (int i = 0; i < count; i++) {
			int randomIndex = ThreadLocalRandom.current().nextInt(cards.length);
			deck.add(cards[randomIndex].clone());
		}
		return deck;
	}
	
	public static CardCollection getRandomDeck(HeroClass heroClass) {
		CardCollection deck = new CardCollection();
		CardCollection classCards = CardCatalogue.query(null, null, heroClass);
		
		for (Card card : classCards) {
			deck.add(card.clone());
			if (card.getRarity() != Rarity.LEGENDARY && Math.random() < 0.5) {
				deck.add(card.clone());
			}
		}
		CardCollection neutralCards = CardCatalogue.query(null, null, HeroClass.ANY);
		
		while (deck.getCount() < 30) {
			Card randomCard = neutralCards.getRandom();
			neutralCards.remove(randomCard);
			deck.add(randomCard.clone());
			if (randomCard.getRarity() != Rarity.LEGENDARY && Math.random() < 0.5) {
				deck.add(randomCard.clone());
			}
			
		}
		
		return deck;
	}
	

}
