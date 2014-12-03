import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PhysicalAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.DireWolfAlpha;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue.Assassinate;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock.Hellfire;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Garrosh;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Jaina;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.aura.BuffAura;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AuraTests extends BasicTests {

	@Test
	public void testAdjacentAura() {
		GameContext context = createContext(new Jaina(), new Garrosh());
		Player player = context.getPlayer1();

		TestMinionCard minionCard = new TestMinionCard(1, 1);
		Minion testMinion1 = playMinionCard(context, player, minionCard);

		MinionCard direWolfCard = new DireWolfAlpha();
		Minion direWolf = playMinionCard(context, player, direWolfCard);

		minionCard = new TestMinionCard(5, 5);
		Minion testMinion2 = playMinionCard(context, player, minionCard);
		minionCard = new TestMinionCard(5, 5);
		Minion testMinion3 = playMinionCard(context, player, minionCard);

		Assert.assertEquals(direWolf.getAttack(), 2);
		Assert.assertEquals(testMinion1.getAttack(), 2);
		Assert.assertEquals(testMinion2.getAttack(), 6);
		Assert.assertEquals(testMinion3.getAttack(), 5);

		Card destroyCard = new Assassinate();
		context.getLogic().receiveCard(player.getId(), destroyCard);
		GameAction destroyAction = destroyCard.play();
		destroyAction.setTarget(testMinion2);
		context.getLogic().performGameAction(player.getId(), destroyAction);
		Assert.assertEquals(testMinion1.getAttack(), 2);
		Assert.assertEquals(direWolf.getAttack(), 2);
		Assert.assertEquals(testMinion3.getAttack(), 6);

		playCard(context, player, new Hellfire());
		Assert.assertEquals(direWolf.getAttack(), 2);
		Assert.assertEquals(testMinion3.getAttack(), 5);
	}

	@Test
	public void testAura() {
		GameContext context = createContext(new Jaina(), new Garrosh());
		Player player = context.getPlayer1();
		Player opponent = context.getPlayer2();

		TestMinionCard minionCard = new TestMinionCard(1, 1);
		minionCard.getMinion().setSpellTrigger(new BuffAura(1, 1, EntityReference.FRIENDLY_MINIONS));
		playCard(context, player, minionCard);

		Actor minion1 = getSingleMinion(player.getMinions());
		Assert.assertEquals(minion1.getAttack(), 1);

		minionCard = new TestMinionCard(1, 1);
		minionCard.getMinion().setSpellTrigger(new BuffAura(1, 1, EntityReference.FRIENDLY_MINIONS));
		Actor minion2 = playMinionCard(context, player, minionCard);

		Assert.assertNotEquals(minion1, minion2);
		Assert.assertEquals(minion1.getAttack(), 2);
		Assert.assertEquals(minion2.getAttack(), 2);

		TestMinionCard minionCardOpponent = new TestMinionCard(3, 3);
		Actor enemyMinion = playMinionCard(context, opponent, minionCardOpponent);
		Assert.assertEquals(enemyMinion.getAttack(), 3);

		Assert.assertEquals(minion1.getAttack(), 2);
		Assert.assertEquals(minion2.getAttack(), 2);
		PhysicalAttackAction attackAction = new PhysicalAttackAction(enemyMinion.getReference());
		attackAction.setTarget(minion2);
		context.getLogic().performGameAction(opponent.getId(), attackAction);
		Assert.assertEquals(minion1.getAttack(), 1);

		minionCard = new TestMinionCard(1, 1);
		minion2 = playMinionCard(context, player, minionCard);
		Assert.assertEquals(minion1.getAttack(), 1);
		Assert.assertEquals(minion2.getAttack(), 2);
	}

}
