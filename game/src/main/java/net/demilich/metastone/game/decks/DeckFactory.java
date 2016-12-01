package net.demilich.metastone.game.decks;

import java.util.concurrent.ThreadLocalRandom;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import org.apache.commons.lang3.RandomUtils;

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

	public static Deck getRandomDeck(HeroClass heroClass, DeckFormat deckFormat) {
		return new RandomDeck(heroClass, deckFormat);
	}

	public static Deck getRandomDeck() {
		HeroClass[] heroClasses = {HeroClass.DRUID, HeroClass.HUNTER, HeroClass.MAGE, HeroClass.PALADIN, HeroClass.PRIEST, HeroClass.ROGUE, HeroClass.SHAMAN, HeroClass.WARLOCK, HeroClass.WARRIOR};
		Deck randomDeck = DeckFactory.getRandomDeck(
				heroClasses[RandomUtils.nextInt(0, heroClasses.length)],
				new DeckFormat().withCardSets(
						CardSet.PROCEDURAL_PREVIEW,
						CardSet.BASIC,
						CardSet.CLASSIC));
		return randomDeck;
	}
}
