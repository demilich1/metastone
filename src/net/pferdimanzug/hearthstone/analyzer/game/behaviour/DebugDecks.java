package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCatalogue;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;

public class DebugDecks {
	
	public static CardCollection getRandomDeck(HeroClass heroClass) {
		CardCollection deck = new CardCollection();
		CardCollection classCards = CardCatalogue.query(null, Rarity.FREE, heroClass);
		for (Card commonCard : CardCatalogue.query(null, Rarity.COMMON, heroClass)) {
			classCards.add(commonCard);
		}
		for (Card card : classCards) {
			deck.add(card.clone());
			if (Math.random() < 0.5) {
				deck.add(card.clone());
			}
		}
		CardCollection neutralCards = CardCatalogue.query(null, Rarity.FREE, HeroClass.ANY);
		for (Card card : CardCatalogue.query(null, Rarity.COMMON, HeroClass.ANY)) {
			neutralCards.add(card);
		}
		while (deck.getCount() < 30) {
			Card randomCard = neutralCards.getRandom();
			neutralCards.remove(randomCard);
			deck.add(randomCard.clone());
			deck.add(randomCard.clone());
		}
		
		return deck;
	}
	
	public static CardCollection getSingleCardDeck(Card card, int count) {
		CardCollection deck = new CardCollection();
		for (int i = 0; i < count; i++) {
			deck.add(card.clone());
		}
		return deck;
	}
	

}
