package net.pferdimanzug.hearthstone.analyzer.game.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CardCollection implements Iterable<Card>, Cloneable {

	private List<Card> cards = new ArrayList<Card>();

	public CardCollection() {

	}

	public void add(Card card) {
		cards.add(card);
	}

	public void addAfter(Card card, Card after) {
		int index = cards.indexOf(after);
		cards.add(index + 1, card);
	}

	public void addAll(CardCollection cardCollection) {
		for (Card card : cardCollection) {
			cards.add(card.clone());
		}
	}

	public CardCollection clone() {
		CardCollection clone = new CardCollection();
		clone.addAll(this);
		return clone;
	}

	public boolean contains(Card card) {
		return cards.contains(card);
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
			if (card.getCardType() == cardType) {
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
			if (card.getCardType() == cardType) {
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

	public Card removeFirst() {
		return cards.remove(0);
	}

	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	public List<Card> toList() {
		return new ArrayList<>(cards);
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

}
