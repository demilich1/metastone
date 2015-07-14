package net.demilich.metastone.tests;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;


public class UniqueIdTest {
	
	@Test
	public void testUniqueCardIds() {
		HashMap<Integer, Card> cardIds = new HashMap<>();
		for (Card card : CardCatalogue.getAll()) {
			Integer id = card.getTypeId();
			System.out.println("Card " + card.getName() + " Id: " + id);
			Assert.assertFalse(cardIds.containsKey(id), "Card " + card + " has same id as " + cardIds.get(id));
			cardIds.put(id, card);
		}
	}

}
