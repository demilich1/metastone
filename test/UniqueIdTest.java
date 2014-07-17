import java.util.HashMap;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCatalogue;

import org.testng.Assert;
import org.testng.annotations.Test;


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
