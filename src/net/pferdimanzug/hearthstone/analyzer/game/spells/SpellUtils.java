package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;

public class SpellUtils {

	public static CardCollection getCards(CardCollection source, Predicate<Card> filter) {
		CardCollection result = new CardCollection();
		for (Card card : source) {
			if (filter.test(card)) {
				result.add(card);
			}
		}
		return result;
	}

	public static <T> T getRandomTarget(List<T> targets) {
		int randomIndex = ThreadLocalRandom.current().nextInt(targets.size());
		return targets.get(randomIndex);
	}

	private SpellUtils() {
	}

}
