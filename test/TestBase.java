import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PhysicalAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.IBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.decks.DeckFactory;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;


public class TestBase {
	
	private static class NullBehaviour implements IBehaviour {

		@Override
		public String getName() {
			return "Null Behaviour";
		}

		@Override
		public List<Card> mulligan(GameContext context, Player player, List<Card> cards) {
			return new ArrayList<Card>();
		}

		@Override
		public Entity provideTargetFor(Player player, GameAction action) {
			return null;
		}

		@Override
		public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
			return null;
		}
		
	}
	
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
		logic.init(GameContext.PLAYER_1, true);
		logic.init(GameContext.PLAYER_2, false);
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
	
	protected static Minion getSingleMinion(List<Minion> minions, Integer typeId) {
		for (Minion minion : minions) {
			if (minion.getTypeId() == typeId) {
				return minion;
			}
		}
		return null;
	}
	
	protected static void playCard(GameContext context, Player player, Card card) {
		context.getLogic().receiveCard(player.getId(), card);
		context.getLogic().performGameAction(player.getId(), card.play());
	}
	
	protected static Minion playMinionCard(GameContext context, Player player, MinionCard minionCard) {
		context.getLogic().receiveCard(player.getId(), minionCard);
		context.getLogic().performGameAction(player.getId(), minionCard.play());
		return getSingleMinion(player.getMinions(), minionCard.summon().getTypeId());
	}

}
