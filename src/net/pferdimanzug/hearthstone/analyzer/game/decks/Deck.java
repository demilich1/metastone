package net.pferdimanzug.hearthstone.analyzer.game.decks;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;

public class Deck {
	
	private String name = "";
	private final HeroClass heroClass;
	private final CardCollection cards = new CardCollection();
	private String description;

	public Deck(HeroClass heroClass) {
		this.heroClass = heroClass;
	}

	public int containsHowMany(Card card) {
		int count = 0;
		for (Card cardInDeck : cards) {
			if (card.getTypeId() == cardInDeck.getTypeId()) {
				count++;
			}
		}
		return count;
	}

	public CardCollection getCards() {
		return cards;
	}

	public String getDescription() {
		return description;
	}

	public HeroClass getHeroClass() {
		return heroClass;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isComplete() {
		return getCards().getCount() == GameLogic.DECK_SIZE;
	}
}
