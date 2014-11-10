import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PhysicalAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.Behaviour;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.decks.DeckFactory;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestBase {
	
	protected static void attack(GameContext context, Player player, Entity attacker, Entity target) {
		PhysicalAttackAction physicalAttackAction = new PhysicalAttackAction(attacker.getReference());
		physicalAttackAction.setTarget(target);
		context.getLogic().performGameAction(player.getId(), physicalAttackAction);
	}
	
	protected static DebugContext createContext(Hero hero1, Hero hero2) {
		Player player1 = new Player("P1", hero1, DeckFactory.getRandomDeck(hero1.getHeroClass()));
		player1.setBehaviour(new NullBehaviour());
		Player player2 = new Player("P2", hero2, DeckFactory.getRandomDeck(hero2.getHeroClass()));
		player2.setBehaviour(new NullBehaviour());
		GameLogic logic = new GameLogic();
		DebugContext context = new DebugContext(player1, player2, logic);
		logic.setContext(context);
		context.init();
		return context;
	}
	
	protected static Actor getSingleMinion(List<Minion> minions) {
		for (Actor minion : minions) {
			if (minion == null) {
				continue;
			}
			return minion;
		}
		return null;
	}
	
	protected static Minion getSummonedMinion(List<Minion> minions) {
		List<Minion> minionList = new ArrayList<>(minions);
		Collections.sort(minionList, (m1, m2) -> Integer.compare(m1.getId(), m2.getId()));
		return minionList.get(minionList.size() - 1);
	}
	
	protected static void playCard(GameContext context, Player player, Card card) {
		context.getLogic().receiveCard(player.getId(), card);
		context.getLogic().performGameAction(player.getId(), card.play());
	}
	
	protected static Minion playMinionCard(GameContext context, Player player, MinionCard minionCard) {
		context.getLogic().receiveCard(player.getId(), minionCard);
		context.getLogic().performGameAction(player.getId(), minionCard.play());
		return getSummonedMinion(player.getMinions());
	}
	
	protected static Logger logger = LoggerFactory.getLogger(TestBase.class);
	
	private static class NullBehaviour extends Behaviour {

		@Override
		public String getName() {
			return "Null Behaviour";
		}

		@Override
		public List<Card> mulligan(GameContext context, Player player, List<Card> cards) {
			return new ArrayList<Card>();
		}

		@Override
		public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
			return validActions.get(0);
		}
		
	}

}
