package net.pferdimanzug.hearthstone.analyzer.game.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CardCollection<T extends Card> implements Iterable<T>{
	
	private List<T> cards = new ArrayList<T>();
	
	public void add(T card) {
		cards.add(card);
	}
	
	public boolean remove(T card) {
		return cards.remove(card);
	}
	
	public void removeAll() {
		cards.clear();
	}
	
	public boolean isEmpty() {
		return cards.isEmpty();
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	public T removeFirst() {
		return cards.remove(0);
	}
	
	public int getCount() {
		return cards.size();
	}
	
	public void addAfter(T card, T after) {
		int index = cards.indexOf(after);
		cards.add(index + 1, card);
	}
	
	public boolean contains(T card) {
		return cards.contains(card);
	}
	
	public Card getRandom() {
		if (cards.isEmpty()) {
			return null;
		}
		return cards.get(ThreadLocalRandom.current().nextInt(cards.size()));
	}

	@Override
	public Iterator<T> iterator() {
		return cards.iterator();
	}

}
