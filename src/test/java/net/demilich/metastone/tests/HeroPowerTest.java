package net.demilich.metastone.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class HeroPowerTest extends TestBase {

	@Test
	public void testArmorUp() {
		GameContext context = createContext(HeroClass.WARRIOR, HeroClass.MAGE);
		final Hero warrior = context.getPlayer1().getHero();

		Assert.assertEquals(warrior.getHp(), GameLogic.MAX_HERO_HP);

		GameAction armorUp = warrior.getHeroPower().play();
		context.getLogic().performGameAction(context.getPlayer1().getId(), armorUp);

		int armorUpBonus = 2;
		Assert.assertEquals(warrior.getHp(), GameLogic.MAX_HERO_HP);
		Assert.assertEquals(warrior.getArmor(), armorUpBonus);

		SpellDesc damage = DamageSpell.create(EntityReference.FRIENDLY_HERO, 2 * armorUpBonus);
		playCard(context, context.getPlayer1(), new TestSpellCard(damage));
		Assert.assertEquals(warrior.getHp(), GameLogic.MAX_HERO_HP - armorUpBonus);
		Assert.assertEquals(warrior.getArmor(), 0);

		// there was a bug where armor actually increased the hp of the hero
		// when
		// the damage dealt was less than the total armor. Following test
		// covers that scenario
		context.getLogic().performGameAction(context.getPlayer1().getId(), armorUp);
		damage = DamageSpell.create(EntityReference.FRIENDLY_HERO, armorUpBonus / 2);
		playCard(context, context.getPlayer1(), new TestSpellCard(damage));

		Assert.assertEquals(warrior.getHp(), GameLogic.MAX_HERO_HP - armorUpBonus);
		Assert.assertEquals(warrior.getArmor(), armorUpBonus / 2);
	}

	@Test
	public void testFireblast() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Hero mage = context.getPlayer1().getHero();
		Hero victim = context.getPlayer2().getHero();
		Assert.assertEquals(victim.getHp(), GameLogic.MAX_HERO_HP);

		GameAction fireblast = mage.getHeroPower().play();
		fireblast.setTarget(victim);
		final int fireblastDamage = 1;
		context.getLogic().performGameAction(context.getPlayer1().getId(), fireblast);
		Assert.assertEquals(victim.getHp(), GameLogic.MAX_HERO_HP - fireblastDamage);
	}

	@Test
	public void testLesserHeal() {
		GameContext context = createContext(HeroClass.PRIEST, HeroClass.WARRIOR);
		Hero priest = context.getPlayer1().getHero();

		int lesserHealing = 2;
		priest.setHp(GameLogic.MAX_HERO_HP - lesserHealing);
		Assert.assertEquals(priest.getHp(), GameLogic.MAX_HERO_HP - lesserHealing);

		GameAction lesserHeal = priest.getHeroPower().play();
		lesserHeal.setTarget(priest);
		context.getLogic().performGameAction(context.getPlayer1().getId(), lesserHeal);
		Assert.assertEquals(priest.getHp(), GameLogic.MAX_HERO_HP);
		context.getLogic().performGameAction(context.getPlayer1().getId(), lesserHeal);
		Assert.assertEquals(priest.getHp(), GameLogic.MAX_HERO_HP);
	}

	@Test
	public void testLifeTap() {
		GameContext context = createContext(HeroClass.WARLOCK, HeroClass.WARRIOR);
		Player warlockPlayer = context.getPlayer1();
		Hero warlock = warlockPlayer.getHero();

		Assert.assertEquals(warlock.getHp(), GameLogic.MAX_HERO_HP);

		int cardCount = warlockPlayer.getHand().getCount();
		GameAction lifetap = warlock.getHeroPower().play();
		context.getLogic().performGameAction(warlockPlayer.getId(), lifetap);

		final int lifeTapDamage = 2;
		Assert.assertEquals(warlock.getHp(), GameLogic.MAX_HERO_HP - lifeTapDamage);
		Assert.assertEquals(warlockPlayer.getHand().getCount(), cardCount + 1);
	}
}
