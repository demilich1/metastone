package net.demilich.metastone.game.cards;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class CardCollection implements Iterable<Card>, Cloneable, Serializable {
	private static final long serialVersionUID = 1L;

	private List<Card> cards = new ArrayList<Card>();

	public CardCollection() {
	}

	public CardCollection add(Card card) {
		cards.add(card);
		return this;
	}

	public CardCollection addAll(CardCollection cardCollection) {
		for (Card card : cardCollection) {
			cards.add(card.clone());
		}
		return this;
	}

	public void addRandomly(Card card) {
		int index = ThreadLocalRandom.current().nextInt(cards.size() + 1);
		cards.add(index, card);
	}

	public CardCollection clone() {
		CardCollection clone = new CardCollection();
		for (Card card : cards) {
			clone.add(card.clone());
		}

		return clone;
	}

	public boolean contains(Card card) {
		return cards.contains(card);
	}

	public boolean containsCard(Card card) {
		if (card == null) {
			return false;
		}
		for (Card other : cards) {
			if (other.getCardId().equals(card.getCardId())) {
				return true;
			}
		}
		return false;
	}

	public Card get(int index) {
		return cards.get(index);
	}

	public int getCount() {
		return cards.size();
	}

	public Card getRandom() {
		if (cards.isEmpty()) {
			return null;
		}
		return cards.get(ThreadLocalRandom.current().nextInt(cards.size()));
	}

	public Card getRandomOfType(CardType cardType) {
		List<Card> relevantCards = new ArrayList<>();
		for (Card card : cards) {
			if (card.getCardType().isCardType(cardType)) {
				relevantCards.add(card);
			}
		}
		if (relevantCards.isEmpty()) {
			return null;
		}
		return relevantCards.get(ThreadLocalRandom.current().nextInt(relevantCards.size()));
	}

	public boolean hasCardOfType(CardType cardType) {
		for (Card card : cards) {
			if (card.getCardType().isCardType(cardType)) {
				return true;
			}
		}
		return false;
	}

	public boolean isEmpty() {
		return cards.isEmpty();
	}

	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}

	public Card peekFirst() {
		return cards.get(0);
	}

	public boolean remove(Card card) {
		return cards.remove(card);
	}

	public void removeAll() {
		cards.clear();
	}

	public void removeAll(Predicate<Card> filter) {
		cards.removeIf(filter);
	}

	public Card removeFirst() {
		return cards.remove(0);
	}

	public boolean replace(Card oldCard, Card newCard) {
		int index = cards.indexOf(oldCard);
		if (index != -1) {
			cards.set(index, newCard);
			return true;
		}
		return false;
	}

	public void shuffle() {
		Collections.shuffle(cards);
	}

	public void shuffle(Random random) {
		Collections.shuffle(cards, random);
	}

	public void sortByManaCost() {
		Comparator<Card> manaComparator = new Comparator<Card>() {

			@Override
			public int compare(Card card1, Card card2) {
				Integer manaCost1 = card1.getBaseManaCost();
				Integer manaCost2 = card2.getBaseManaCost();
				return manaCost1.compareTo(manaCost2);
			}
		};
		cards.sort(manaComparator);
	}

	public void sortByName() {
		cards.sort((card1, card2) -> card1.getName().compareTo(card2.getName()));
	}

	public List<Card> toList() {
		return new ArrayList<>(cards);
	}

}
