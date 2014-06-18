package net.pferdimanzug.hearthstone.analyzer.game.cards;

import java.util.ArrayList;
import java.util.Collections;
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

}
