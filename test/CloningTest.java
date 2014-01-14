import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.DebugDecks;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.PlayRandomBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Garrosh;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Jaina;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;

import org.testng.Assert;
import org.testng.annotations.Test;


public class CloningTest extends TestBase {
	
	
	@Test
	public void testCloning() {
		Hero hero1 = new Jaina();
		Player player1 = new Player("Random dude 1", hero1, DebugDecks.getRandomDeck(hero1.getHeroClass()));
		player1.setBehaviour(new PlayRandomBehaviour());
		Hero hero2 = new Garrosh();
		Player player2 = new Player("Random dude 2", hero2, DebugDecks.getRandomDeck(hero2.getHeroClass()));
		player2.setBehaviour(new PlayRandomBehaviour());
		GameLogic gameLogic = new GameLogic();
		GameContext original = new GameContext(player1, player2, gameLogic);
		gameLogic.setContext(original);
		GameContext clone = original.clone();
		
		Assert.assertNotEquals(original, clone);
		Assert.assertNotEquals(original.getPlayer1(), clone.getPlayer2());
		Assert.assertNotSame(original.getPlayer2().getMinions(), clone.getPlayer2().getMinions());
		Assert.assertEquals(original.getPlayer2().getMinions().size(), clone.getPlayer2().getMinions().size());
		Assert.assertEquals(original.getPlayer1().getMana(), clone.getPlayer1().getMana());
		
		clone.play();
		Assert.assertNotEquals(original.getTurn(), clone.getTurn());
	}
}
