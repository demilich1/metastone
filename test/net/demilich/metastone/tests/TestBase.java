package net.demilich.metastone.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.actions.PhysicalAttackAction;
import net.demilich.metastone.game.behaviour.Behaviour;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardProxy;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.HeroCard;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.decks.DeckFactory;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.gui.gameconfig.PlayerConfig;

public class TestBase {

	static {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.DEBUG);
		
		new CardProxy();
	}

	protected static void attack(GameContext context, Player player, Entity attacker, Entity target) {
		PhysicalAttackAction physicalAttackAction = new PhysicalAttackAction(attacker.getReference());
		physicalAttackAction.setTarget(target);
		context.getLogic().performGameAction(player.getId(), physicalAttackAction);
	}

	protected static DebugContext createContext(HeroClass hero1, HeroClass hero2) {
		PlayerConfig player1Config = new PlayerConfig(DeckFactory.getRandomDeck(hero1), new NullBehaviour());
		player1Config.setName("Player 1");
		player1Config.setHeroCard(getHeroCardForClass(hero1));
		Player player1 = new Player(player1Config);

		PlayerConfig player2Config = new PlayerConfig(DeckFactory.getRandomDeck(hero2), new NullBehaviour());
		player2Config.setName("Player 2");
		player2Config.setHeroCard(getHeroCardForClass(hero2));
		Player player2 = new Player(player2Config);

		GameLogic logic = new GameLogic();
		DebugContext context = new DebugContext(player1, player2, logic);
		logic.setContext(context);
		context.init();
		return context;
	}
	
	protected static HeroCard getHeroCardForClass(HeroClass heroClass) {
		for(Card card : CardCatalogue.query(CardType.HERO)) {
			HeroCard heroCard = (HeroCard) card;
			if (heroCard.getHeroClass() == heroClass) {
				return heroCard;
			}
		}
		return null;
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
