package net.demilich.metastone.tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.EntityReference;

public class TechnicalTests extends TestBase {

	@Test
	public void testDoubleCorruption() {
		GameContext context = createContext(HeroClass.WARLOCK, HeroClass.WARRIOR);
		Player warlock = context.getPlayer1();
		warlock.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		TestMinionCard victimCard = new TestMinionCard(1, 10);
		context.getLogic().receiveCard(warrior.getId(), victimCard);
		context.getLogic().performGameAction(warrior.getId(), victimCard.play());

		Card corruption1 = CardCatalogue.getCardById("spell_corruption");
		Card corruption2 = CardCatalogue.getCardById("spell_corruption");
		context.getLogic().receiveCard(warlock.getId(), corruption1);
		context.getLogic().receiveCard(warlock.getId(), corruption2);

		Entity victim = getSingleMinion(warrior.getMinions());
		GameAction playCorruption1 = corruption1.play();
		playCorruption1.setTarget(victim);
		GameAction playCorruption2 = corruption2.play();
		playCorruption2.setTarget(victim);

		context.getLogic().performGameAction(warlock.getId(), playCorruption1);
		context.getLogic().performGameAction(warlock.getId(), playCorruption2);

		context.getLogic().endTurn(GameContext.PLAYER_1);
		context.getLogic().startTurn(GameContext.PLAYER_2);
		context.getLogic().endTurn(GameContext.PLAYER_2);
		context.getLogic().startTurn(GameContext.PLAYER_1);
	}

	@Test
	public void testTriplePyromancer() {
		GameContext context = createContext(HeroClass.PRIEST, HeroClass.WARRIOR);
		Player player1 = context.getPlayer1();
		player1.setMana(10);

		for (int i = 0; i < 3; i++) {
			playCard(context, player1, CardCatalogue.getCardById("minion_wild_pyromancer"));
		}

		List<Minion> copyOfMinionList = new ArrayList<Minion>();
		copyOfMinionList.addAll(player1.getMinions());
		Assert.assertEquals(copyOfMinionList.size(), 3);
		// three pyromancers summoned, all should be at max HP
		for (Minion minion : copyOfMinionList) {
			Assert.assertEquals(minion.getHp(), minion.getMaxHp());
		}

		playCard(context, player1, new TestSpellCard(DamageSpell.create(EntityReference.ENEMY_HERO, 1)));
		// after playing a spell:
		// all three pyromancers should have triggered, even though all are dead
		// after the first two
		// this net.demilich.metastone.tests that minions are only removed from board after all effects
		// are resolved
		for (Minion minion : copyOfMinionList) {
			Assert.assertEquals(minion.getHp(), -1);
		}
	}
}
