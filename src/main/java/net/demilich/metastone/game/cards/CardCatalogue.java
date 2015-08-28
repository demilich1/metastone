package net.demilich.metastone.game.cards;

import java.util.function.Predicate;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.entities.heroes.HeroClass;

public class CardCatalogue {

	private final static CardCollection cards = new CardCollection();


	public static void add(Card card) {
		cards.add(card);
	}

	public static CardCollection getAll() {
		CardCollection result = new CardCollection();
		for (Card card : cards) {
			result.add(card);
		}
		return result;
	}

	public static Card getCardById(String id) {
		for (Card card : cards) {
			if (card.getCardId() != null && card.getCardId().equalsIgnoreCase(id)) {
				return card.clone();
			}
		}

		return null;
	}

	public static Card getCardByName(String name) {
		for (Card card : cards) {
			if (card.getName().equals(name)) {
				return card.clone();
			}
		}

		return null;
	}

	public static CardCollection getHeroes() {
		return query(card -> card.isCollectible() && card.getCardType() == CardType.HERO);
	}

	public static CardCollection query(Attribute tag) {
		return query(null, null, null, tag);
	}

	public static CardCollection query(CardType cardType) {
		return query(cardType, null, null);
	}

	public static CardCollection query(CardType cardType, Rarity rarity, HeroClass heroClass) {
		return query(cardType, rarity, heroClass, null);
	}

	public static CardCollection query(CardType cardType, Rarity rarity, HeroClass heroClass, Attribute tag) {
		CardCollection result = new CardCollection();
		for (Card card : cards) {
			if (!card.isCollectible()) {
				continue;
			}
			if (cardType != null && card.getCardType() != cardType) {
				continue;
			}
			// per default, do not include heroes or hero powers
			if (card.getCardType() == CardType.HERO_POWER || card.getCardType() == CardType.HERO) {
				continue;
			}
			if (rarity != null && card.getRarity() != rarity) {
				continue;
			}
			if (heroClass != null && card.getClassRestriction() != heroClass) {
				continue;
			}
			if (tag != null && !card.hasAttribute(tag)) {
				continue;
			}
			result.add(card.clone());
		}

		return result;
	}

	public static CardCollection query(Predicate<Card> filter) {
		CardCollection result = new CardCollection();
		for (Card card : cards) {
			if (filter.test(card)) {
				result.add(card);
			}
		}
		return result;
	}
}
