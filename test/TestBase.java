import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.DebugDecks;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;


public class TestBase {
	
	protected static GameContext createContext(Hero hero1, Hero hero2) {
		
		Player player1 = new Player("P1", hero1, DebugDecks.getRandomDeck(hero1.getHeroClass()));
		Player player2 = new Player("P2", hero2, DebugDecks.getRandomDeck(hero2.getHeroClass()));
		GameLogic logic = new GameLogic();
		GameContext context = new GameContext(player1, player2, logic);
		logic.setContext(context);
		logic.init(player1, true);
		logic.init(player2, false);
		return context;
	}
	
	
	protected static Entity getSingleMinion(List<Minion> minions) {
		for (Entity minion : minions) {
			if (minion == null) {
				continue;
			}
			return minion;
		}
		return null;
	}

}
