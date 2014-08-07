import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.PlayRandomBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage.Polymorph;
import net.pferdimanzug.hearthstone.analyzer.game.decks.DeckFactory;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Garrosh;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Jaina;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;

import org.testng.Assert;
import org.testng.annotations.Test;


public class CloningTest extends TestBase {
	
	
	private void compareCardCollections(CardCollection collection1, CardCollection collection2) {
		Assert.assertEquals(collection1.getCount(), collection2.getCount());
		Assert.assertNotSame(collection1, collection2);
		for (int j = 0; j < collection1.getCount(); j++) {
			Card originalCard = collection1.get(j);
			logger.debug("Original card: " + originalCard);
			Card cloneCard = collection2.get(j);
			logger.debug("Clone card: " + cloneCard);
			Assert.assertNotSame(originalCard, cloneCard);
			if (originalCard instanceof SpellCard) {
				Assert.assertTrue(cloneCard instanceof SpellCard, "cloneCard is instanceof " + cloneCard.getClass().getSimpleName());
				SpellCard originalSpellCard = (SpellCard) originalCard;
				SpellCard cloneSpellCard = (SpellCard) cloneCard;
				Assert.assertNotSame(originalSpellCard.getSpell(), cloneSpellCard.getSpell());
			}
		}
	}
	
	@Test
	public void testCloneSpellCard() {
		Card original = new Polymorph();
		Card clone = original.clone();
		Assert.assertNotSame(original, clone);
		SpellCard originalSpellCard = (SpellCard) original;
		SpellCard cloneSpellCard = (SpellCard) clone;
		Assert.assertNotSame(originalSpellCard.getSpell(), cloneSpellCard.getSpell());
	}
	
	@Test
	public void testCloning() {
		for (int i = 0; i < 100; i++) {
			Hero hero1 = new Jaina();
			Player player1 = new Player("Random dude 1", hero1, DeckFactory.getRandomDeck(hero1.getHeroClass()));
			player1.setBehaviour(new PlayRandomBehaviour());
			Hero hero2 = new Garrosh();
			Player player2 = new Player("Random dude 2", hero2, DeckFactory.getRandomDeck(hero2.getHeroClass()));
			player2.setBehaviour(new PlayRandomBehaviour());
			GameContext original = new GameContext(player1, player2, new GameLogic());
			TestMinionCard minionCard = new TestMinionCard(3, 3);
			original.getLogic().receiveCard(player1.getId(), minionCard);
			original.getLogic().performGameAction(player1.getId(), minionCard.play());
			Actor testMinion = minionCard.getMinion();
			
			GameContext clone = original.clone();
			
			Assert.assertNotSame(original, clone);
			Assert.assertNotSame(original.getPlayer1(), clone.getPlayer1());
			
			logger.debug("Comparing hands");
			compareCardCollections(original.getPlayer1().getHand(), clone.getPlayer1().getHand());
			logger.debug("Comparing decks");
			compareCardCollections(original.getPlayer1().getDeck(), clone.getPlayer1().getDeck());
			
			GameContext cloneOfClone = clone.clone();
			compareCardCollections(clone.getPlayer1().getHand(), cloneOfClone.getPlayer1().getHand());
			compareCardCollections(clone.getPlayer1().getDeck(), cloneOfClone.getPlayer1().getDeck());
			
			Assert.assertNotSame(original.getPlayer2().getMinions(), clone.getPlayer2().getMinions());
			Actor originalMinion = getSingleMinion(original.getPlayer1().getMinions());
			Actor cloneMinion = getSingleMinion(clone.getPlayer1().getMinions());
			Assert.assertNotSame(originalMinion, cloneMinion);
			Assert.assertEquals(original.getPlayer2().getMinions().size(), clone.getPlayer2().getMinions().size());
			Assert.assertEquals(original.getPlayer1().getMana(), clone.getPlayer1().getMana());
			
			clone.play();
			logger.info("");
			logger.info("********ORIGINAL********");
			logger.info(original.toString());
			logger.info("");
			logger.info("********CLONE********");
			logger.info(clone.toString());	
			Assert.assertNotEquals(original.getTurn(), clone.getTurn());
			Assert.assertEquals(testMinion.getHp(), 3);
		}
	}
}
