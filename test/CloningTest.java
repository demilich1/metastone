import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.PlayRandomBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.decks.DeckFactory;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Garrosh;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Jaina;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;

import org.testng.Assert;
import org.testng.annotations.Test;


public class CloningTest extends TestBase {
	
	
	@Test
	public void testCloning() {
		for (int i = 0; i < 100; i++) {
			Hero hero1 = new Jaina();
			Player player1 = new Player("Random dude 1", hero1, DeckFactory.getRandomCards(hero1.getHeroClass()));
			player1.setBehaviour(new PlayRandomBehaviour());
			Hero hero2 = new Garrosh();
			Player player2 = new Player("Random dude 2", hero2, DeckFactory.getRandomCards(hero2.getHeroClass()));
			player2.setBehaviour(new PlayRandomBehaviour());
			GameContext original = new GameContext(player1, player2, new GameLogic());
			TestMinionCard minionCard = new TestMinionCard(3, 3);
			original.getLogic().receiveCard(player1.getId(), minionCard);
			original.getLogic().performGameAction(player1.getId(), minionCard.play());
			Actor testMinion = minionCard.getMinion();
			
			GameContext clone = original.clone();
			
			Assert.assertNotEquals(original, clone);
			Assert.assertNotEquals(original.getPlayer1(), clone.getPlayer1());
			Assert.assertNotSame(original.getPlayer2().getMinions(), clone.getPlayer2().getMinions());
			Actor originalMinion = getSingleMinion(original.getPlayer1().getMinions());
			Actor cloneMinion = getSingleMinion(clone.getPlayer1().getMinions());
			Assert.assertNotSame(originalMinion, cloneMinion);
			System.out.println(originalMinion.toString());
			System.out.println(cloneMinion.toString());
			Assert.assertEquals(original.getPlayer2().getMinions().size(), clone.getPlayer2().getMinions().size());
			Assert.assertEquals(original.getPlayer1().getMana(), clone.getPlayer1().getMana());
			
			clone.play();
			System.out.println("\n********ORIGINAL********\n");
			System.out.println(original.toString());
			System.out.println("\n********CLONE********\n");
			System.out.println(clone.toString());	
			Assert.assertNotEquals(original.getTurn(), clone.getTurn());
			Assert.assertEquals(testMinion.getHp(), 3);
		}
		
	}
}
