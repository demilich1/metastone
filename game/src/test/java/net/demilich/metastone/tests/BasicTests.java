package net.demilich.metastone.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.actions.PhysicalAttackAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffHeroSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class BasicTests extends TestBase {

	private Card getTheCoin(CardCollection cards) {
		for (Card card : cards) {
			if (card.getCardId().equalsIgnoreCase("spell_the_coin")) {
				return card;
			}
		}
		return null;
	}

	@Test
	public void testBattlecry() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Player mage = context.getPlayer1();
		mage.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		TestMinionCard devMonster = new TestMinionCard(3, 3);
		SpellDesc damageSpell = DamageSpell.create(EntityReference.ENEMY_HERO, 3);
		BattlecryAction testBattlecry = BattlecryAction.createBattlecry(damageSpell);
		testBattlecry.setTarget(warrior.getHero());
		devMonster.getMinion().setBattlecry(testBattlecry);
		context.getLogic().receiveCard(mage.getId(), devMonster);
		context.getLogic().performGameAction(mage.getId(), devMonster.play());

		Assert.assertEquals(warrior.getHero().getHp(), warrior.getHero().getMaxHp() - 3);
	}

	@Test
	public void testHeroAttack() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.DRUID);
		Player mage = context.getPlayer1();
		mage.setMana(10);
		Player druid = context.getPlayer2();
		druid.setMana(10);

		int damage = 1;
		TestMinionCard devMonsterCard = new TestMinionCard(damage, 2);
		playCard(context, mage, devMonsterCard);

		SpellDesc heroBuffSpell = BuffHeroSpell.create(EntityReference.FRIENDLY_HERO, damage, 0);
		context.getLogic().castSpell(druid.getId(), heroBuffSpell, druid.getHero().getReference(), null, false);
		context.getLogic().endTurn(druid.getId());

		Actor devMonster = getSingleMinion(mage.getMinions());
		GameAction minionAttackAction = new PhysicalAttackAction(devMonster.getReference());
		minionAttackAction.setTarget(druid.getHero());
		context.getLogic().performGameAction(mage.getId(), minionAttackAction);
		// monster attacked; it should not be damaged by the hero
		Assert.assertEquals(druid.getHero().getHp(), druid.getHero().getMaxHp() - damage);
		Assert.assertEquals(devMonster.getHp(), devMonster.getMaxHp());
		context.getLogic().endTurn(mage.getId());

		context.getLogic().castSpell(druid.getId(), heroBuffSpell, druid.getHero().getReference(), null, false);
		GameAction heroAttackAction = new PhysicalAttackAction(druid.getHero().getReference());
		heroAttackAction.setTarget(devMonster);
		context.getLogic().performGameAction(mage.getId(), heroAttackAction);
		// hero attacked; both entities should be damaged
		Assert.assertEquals(druid.getHero().getHp(), druid.getHero().getMaxHp() - 2 * damage);
		Assert.assertEquals(devMonster.getHp(), devMonster.getMaxHp() - damage);
	}

	@Test
	public void testMinionAttack() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Player mage = context.getPlayer1();
		mage.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		MinionCard minionCard1 = new TestMinionCard(5, 5);
		context.getLogic().receiveCard(mage.getId(), minionCard1);
		context.getLogic().performGameAction(mage.getId(), minionCard1.play());

		MinionCard minionCard2 = new TestMinionCard(1, 1);
		context.getLogic().receiveCard(warrior.getId(), minionCard2);
		context.getLogic().performGameAction(warrior.getId(), minionCard2.play());

		Assert.assertEquals(mage.getMinions().size(), 1);
		Assert.assertEquals(warrior.getMinions().size(), 1);

		Actor attacker = getSingleMinion(mage.getMinions());
		Actor defender = getSingleMinion(warrior.getMinions());

		GameAction attackAction = new PhysicalAttackAction(attacker.getReference());
		attackAction.setTarget(defender);
		context.getLogic().performGameAction(mage.getId(), attackAction);

		Assert.assertEquals(attacker.getHp(), attacker.getMaxHp() - defender.getAttack());
		Assert.assertEquals(defender.getHp(), defender.getMaxHp() - attacker.getAttack());
		Assert.assertEquals(defender.isDestroyed(), true);

		Assert.assertEquals(mage.getMinions().size(), 1);
		Assert.assertEquals(warrior.getMinions().size(), 0);
	}

	@Test
	public void testSummon() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Player mage = context.getPlayer1();
		mage.getHand().removeAll();
		MinionCard devMonster = new TestMinionCard(1, 1);
		context.getLogic().receiveCard(mage.getId(), devMonster);
		Assert.assertEquals(mage.getHand().getCount(), 1);
		context.getLogic().performGameAction(mage.getId(), devMonster.play());
		Assert.assertEquals(mage.getHand().isEmpty(), true);
		Actor minion = getSingleMinion(mage.getMinions());
		Assert.assertEquals(minion.getName(), devMonster.getName());
		Assert.assertEquals(minion.getAttack(), 1);
		Assert.assertEquals(minion.getHp(), 1);
		Assert.assertEquals(minion.isDestroyed(), false);

		MinionCard devMonster2 = new TestMinionCard(2, 2);
		context.getLogic().receiveCard(mage.getId(), devMonster2);
		GameAction summonAction = devMonster2.play();
		summonAction.setTarget(minion);
		context.getLogic().performGameAction(mage.getId(), summonAction);

		Assert.assertEquals(mage.getMinions().size(), 2);
		Actor left = mage.getMinions().get(0);
		Actor right = mage.getMinions().get(1);
		Assert.assertEquals(left.getAttack(), 2);
		Assert.assertEquals(right.getAttack(), 1);
	}

	@Test
	public void testTheCoin() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Player mage = context.getPlayer1();
		Player warrior = context.getPlayer2();

		Card theCoin = getTheCoin(mage.getHand());
		Assert.assertEquals(theCoin, null);
		theCoin = getTheCoin(warrior.getHand());
		Assert.assertNotEquals(theCoin, null);
	}

}
