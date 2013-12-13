import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.ChillwindYeti;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;


public class TestBase {
	
	protected static GameContext createContext(Hero hero1, Hero hero2) {
		Player player1 = new Player("P1", hero1, getDummyDeck());
		Player player2 = new Player("P2", hero2, getDummyDeck());
		GameLogic logic = new GameLogic();
		GameContext context = new GameContext(player1, player2, logic);
		logic.setContext(context);
		logic.init(player1, true);
		logic.init(player2, false);
		return context;
	}
	
	private static CardCollection<Card> getDummyDeck() {
		CardCollection<Card> deck = new CardCollection<>();
		for (int i = 0; i < 10; i++) {
			deck.add(new ChillwindYeti());
		}
		return deck;
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
