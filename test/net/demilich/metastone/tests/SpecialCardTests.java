package net.demilich.metastone.tests;

import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.actions.PhysicalAttackAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SpecialCardTests extends TestBase {

	@Test
	public void testFaerieDragon() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Player mage = context.getPlayer1();
		mage.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		MinionCard faerieDragonCard = (MinionCard) CardCatalogue.getCardById("minion_faerie_dragon");
		context.getLogic().receiveCard(warrior.getId(), faerieDragonCard);
		context.getLogic().performGameAction(warrior.getId(), faerieDragonCard.play());

		MinionCard devMonsterCard = new TestMinionCard(1, 1);
		context.getLogic().receiveCard(mage.getId(), devMonsterCard);
		context.getLogic().performGameAction(mage.getId(), devMonsterCard.play());

		Entity attacker = getSingleMinion(mage.getMinions());
		Actor elusiveOne = getSingleMinion(warrior.getMinions());

		GameAction attackAction = new PhysicalAttackAction(attacker.getReference());
		List<Entity> validTargets = context.getLogic().getValidTargets(warrior.getId(), attackAction);
		// should be two valid targets: enemy hero and faerie dragon
		Assert.assertEquals(validTargets.size(), 2);

		GameAction useFireblast = mage.getHero().getHeroPower().play();
		validTargets = context.getLogic().getValidTargets(mage.getId(), useFireblast);
		// should be three valid targets, both heroes + minion which is not the
		// faerie dragon
		Assert.assertEquals(validTargets.size(), 3);
		Assert.assertFalse(validTargets.contains(elusiveOne));

		Card arcaneExplosionCard = CardCatalogue.getCardById("spell_arcane_explosion");
		context.getLogic().receiveCard(mage.getId(), arcaneExplosionCard);
		int faerieDragonHp = elusiveOne.getHp();
		context.getLogic().performGameAction(mage.getId(), arcaneExplosionCard.play());
		// hp should been affected after playing area of effect spell
		Assert.assertNotEquals(faerieDragonHp, elusiveOne.getHp());
	}

	@Test
	public void testGurubashiBerserker() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Player mage = context.getPlayer1();
		mage.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		final int BASE_ATTACK = 2;
		final int ATTACK_BONUS = 3;
		
		MinionCard gurubashiBerserkerCard = (MinionCard) CardCatalogue.getCardById("minion_gurubashi_berserker");
		playCard(context, warrior, gurubashiBerserkerCard);

		MinionCard oasisSnapjawCard = (MinionCard) CardCatalogue.getCardById("minion_oasis_snapjaw");
		playCard(context, mage, oasisSnapjawCard);

		Actor attacker = getSingleMinion(mage.getMinions());
		Actor defender = getSingleMinion(warrior.getMinions());

		// Gurubashi Berserker should start with just his base attack
		Assert.assertEquals(defender.getAttack(), BASE_ATTACK);

		// first attack, Gurubashi Berserker should have increased attack
		GameAction attackAction = new PhysicalAttackAction(attacker.getReference());
		attackAction.setTarget(defender);
		context.getLogic().performGameAction(mage.getId(), attackAction);

		Assert.assertEquals(attacker.getHp(), attacker.getMaxHp() - BASE_ATTACK);
		Assert.assertEquals(defender.getHp(), defender.getMaxHp() - attacker.getAttack());
		Assert.assertEquals(defender.getAttack(), BASE_ATTACK + ATTACK_BONUS);

		// second attack, Gurubashi Berserker should become even stronger
		context.getLogic().performGameAction(mage.getId(), attackAction);
		Assert.assertEquals(attacker.getHp(), attacker.getMaxHp() - 2 * BASE_ATTACK - ATTACK_BONUS);
		Assert.assertEquals(defender.getHp(), defender.getMaxHp() - 2 * attacker.getAttack());
		Assert.assertEquals(defender.getAttack(), BASE_ATTACK + 2 * ATTACK_BONUS);
	}

	@Test
	public void testSavageRoar() {
		GameContext context = createContext(HeroClass.DRUID, HeroClass.WARRIOR);
		Player player = context.getPlayer1();
		Hero druid = player.getHero();

		player.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		MinionCard devMonsterCard = new TestMinionCard(1, 1);
		context.getLogic().receiveCard(player.getId(), devMonsterCard);
		context.getLogic().performGameAction(player.getId(), devMonsterCard.play());

		Actor minion = getSingleMinion(player.getMinions());

		context.getLogic().performGameAction(player.getId(), druid.getHeroPower().play());
		Assert.assertEquals(druid.getAttack(), 1);
		Assert.assertEquals(minion.getAttack(), 1);

		Card savageRoar = CardCatalogue.getCardById("spell_savage_roar");
		context.getLogic().receiveCard(player.getId(), savageRoar);
		context.getLogic().performGameAction(player.getId(), savageRoar.play());
		Assert.assertEquals(druid.getAttack(), 3);
		Assert.assertEquals(minion.getAttack(), 3);

		context.getLogic().endTurn(player.getId());
		Assert.assertEquals(druid.getAttack(), 0);
		Assert.assertEquals(minion.getAttack(), 1);

		context.getLogic().endTurn(player.getId());
		Assert.assertEquals(druid.getAttack(), 0);
		Assert.assertEquals(minion.getAttack(), 1);
	}

	@Test
	public void testSpitefulSmith() {
		GameContext context = createContext(HeroClass.WARRIOR, HeroClass.WARRIOR);
		Player player = context.getPlayer1();
		player.setMana(10);

		Card fieryWarAxe = CardCatalogue.getCardById("weapon_fiery_war_axe");
		playCard(context, player, fieryWarAxe);

		Assert.assertTrue(player.getHero().getWeapon() != null);
		Assert.assertEquals(player.getHero().getWeapon().getWeaponDamage(), 3);

		MinionCard spitefulSmithCard = (MinionCard) CardCatalogue.getCardById("minion_spiteful_smith");
		Minion spitefulSmith = playMinionCard(context, player, spitefulSmithCard);
		// Smith has been played, but is not enraged yet, so weapon damage
		// should still be unaltered
		Assert.assertEquals(player.getHero().getWeapon().getWeaponDamage(), 3);

		SpellCard damageSpell = new TestSpellCard(DamageSpell.create(1));
		damageSpell.setTargetRequirement(TargetSelection.ANY);
		context.getLogic().receiveCard(player.getId(), damageSpell);
		GameAction spellAction = damageSpell.play();
		spellAction.setTarget(spitefulSmith);
		context.getLogic().performGameAction(player.getId(), spellAction);

		// Smith is damaged now, so weapon should be buffed
		Assert.assertEquals(player.getHero().getWeapon().getWeaponDamage(), 5);

		// equip a new weapon; this one should get buffed too
		fieryWarAxe = CardCatalogue.getCardById("weapon_fiery_war_axe");
		playCard(context, player, fieryWarAxe);
		Assert.assertEquals(player.getHero().getWeapon().getWeaponDamage(), 5);

		// wipe everything
		SpellDesc wipeSpell = DestroySpell.create(EntityReference.ALL_MINIONS);
		SpellCard wipe = new TestSpellCard(wipeSpell);
		playCard(context, player, wipe);

		// Smith is destroyed, weapon power should be back to normal
		Assert.assertEquals(player.getHero().getWeapon().getWeaponDamage(), 3);
	}

	@Test
	public void testSummoningPortal() {
		GameContext context = createContext(HeroClass.WARLOCK, HeroClass.WARRIOR);
		Player player = context.getPlayer1();
		player.setMana(10);

		Card summoningPortal1 = CardCatalogue.getCardById("minion_summoning_portal");
		context.getLogic().receiveCard(player.getId(), summoningPortal1);
		Card summoningPortal2 = CardCatalogue.getCardById("minion_summoning_portal");
		context.getLogic().receiveCard(player.getId(), summoningPortal2);

		MinionCard testMinionCard = new TestMinionCard(1, 1, 4);
		context.getLogic().receiveCard(player.getId(), testMinionCard);
		Assert.assertEquals(player.getMana(), 10);

		// first summoning portal costs full 4 mana
		context.getLogic().performGameAction(player.getId(), summoningPortal1.play());
		Assert.assertEquals(player.getMana(), 6);

		// second summoning portal affected by first one, costs only 2 mana
		context.getLogic().performGameAction(player.getId(), summoningPortal2.play());
		Assert.assertEquals(player.getMana(), 4);

		// base cost of minion card is 4, reduced by both summoning portals, but
		// not below 1
		context.getLogic().performGameAction(player.getId(), testMinionCard.play());
		Assert.assertEquals(player.getMana(), 3);

	}

}
