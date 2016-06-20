package net.demilich.metastone.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

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

	@Test
	public void testWildPyromancer() {
		GameContext context = createContext(HeroClass.PRIEST, HeroClass.WARRIOR);
		Player warrior = context.getPlayer2();
		Card hauntedCreeper = CardCatalogue.getCardById("minion_haunted_creeper");
		playCard(context, warrior, hauntedCreeper);

		Player priest = context.getPlayer1();
		Card wildPyromancer = CardCatalogue.getCardById("minion_wild_pyromancer");
		playCard(context, priest, wildPyromancer);

		Assert.assertEquals(warrior.getMinions().size(), 1);

		Card holyNova = CardCatalogue.getCardById("spell_holy_nova");
		playCard(context, priest, holyNova);

		// the warriors board should be completely wiped, as the Holy Nova
		// should kill the
		// first body of Haunted Creeper, the Deathrattle resolves and then Wild
		// Pyromancer
		// triggers, clearing the two 1/1 Spectral Spiders
		Assert.assertEquals(warrior.getMinions().size(), 0);
	}

	@Test
	public void testBetrayal() {
		GameContext context = createContext(HeroClass.PALADIN, HeroClass.ROGUE);
		Player paladin = context.getPlayer1();

		MinionCard adjacentMinionCard1 = new TestMinionCard(1, 5, 0);
		Minion adjacentMinion1 = playMinionCard(context, paladin, adjacentMinionCard1);

		MinionCard targetMinionCard = new TestMinionCard(3, 1, 0);
		Minion targetMinion = playMinionCard(context, paladin, targetMinionCard);

		MinionCard adjacentMinionCard2 = new TestMinionCard(1, 5, 0);
		Minion adjacentMinion2 = playMinionCard(context, paladin, adjacentMinionCard2);

		context.getLogic().endTurn(paladin.getId());

		Assert.assertEquals(paladin.getMinions().size(), 3);

		Player rogue = context.getPlayer2();
		Card betrayal = CardCatalogue.getCardById("spell_betrayal");

		context.getLogic().receiveCard(rogue.getId(), betrayal);
		GameAction action = betrayal.play();
		action.setTarget(targetMinion);
		context.getLogic().performGameAction(rogue.getId(), action);
		Assert.assertEquals(targetMinion.getAttack(), 3);
		Assert.assertEquals(targetMinion.getHp(), 1);

		Assert.assertEquals(adjacentMinion1.getHp(), 2);
		Assert.assertEquals(adjacentMinion2.getHp(), 2);

		Assert.assertEquals(paladin.getMinions().size(), 3);
	}

	@Test
	public void testBetrayalNotAffectedBySpellDamage() {
		GameContext context = createContext(HeroClass.PALADIN, HeroClass.ROGUE);
		Player paladin = context.getPlayer1();

		MinionCard adjacentMinionCard1 = new TestMinionCard(1, 5, 0);
		Minion adjacentMinion1 = playMinionCard(context, paladin, adjacentMinionCard1);

		MinionCard targetMinionCard = new TestMinionCard(3, 1, 0);
		Minion targetMinion = playMinionCard(context, paladin, targetMinionCard);

		MinionCard adjacentMinionCard2 = new TestMinionCard(1, 5, 0);
		Minion adjacentMinion2 = playMinionCard(context, paladin, adjacentMinionCard2);

		context.getLogic().endTurn(paladin.getId());

		Player rogue = context.getPlayer2();

		MinionCard azureDrakeCard = (MinionCard) CardCatalogue.getCardById("minion_azure_drake");
		playMinionCard(context, rogue, azureDrakeCard);

		Card betrayal = CardCatalogue.getCardById("spell_betrayal");

		context.getLogic().receiveCard(rogue.getId(), betrayal);
		GameAction action = betrayal.play();
		action.setTarget(targetMinion);
		context.getLogic().performGameAction(rogue.getId(), action);
		Assert.assertEquals(targetMinion.getAttack(), 3);
		Assert.assertEquals(targetMinion.getHp(), 1);

		Assert.assertEquals(adjacentMinion1.getHp(), 2);
		Assert.assertEquals(adjacentMinion2.getHp(), 2);
	}

	@Test
	public void testBetrayalOnEmperorCobraDestroysAdjacentMinions() {
		GameContext context = createContext(HeroClass.PALADIN, HeroClass.ROGUE);
		Player paladin = context.getPlayer1();

		MinionCard adjacentMinionCard1 = new TestMinionCard(1, 5, 0);
		playMinionCard(context, paladin, adjacentMinionCard1);

		MinionCard targetMinionCard = (MinionCard) CardCatalogue.getCardById("minion_emperor_cobra");
		Minion targetMinion = playMinionCard(context, paladin, targetMinionCard);

		MinionCard adjacentMinionCard2 = new TestMinionCard(1, 5, 0);
		playMinionCard(context, paladin, adjacentMinionCard2);

		context.getLogic().endTurn(paladin.getId());

		Assert.assertEquals(paladin.getMinions().size(), 3);

		Player rogue = context.getPlayer2();

		Card betrayal = CardCatalogue.getCardById("spell_betrayal");

		context.getLogic().receiveCard(rogue.getId(), betrayal);
		GameAction action = betrayal.play();
		action.setTarget(targetMinion);
		context.getLogic().performGameAction(rogue.getId(), action);

		Assert.assertEquals(paladin.getMinions().size(), 1);
	}

	@Test
	public void testEydisDarkbane() {
		GameContext context = createContext(HeroClass.PRIEST, HeroClass.WARRIOR);
		Player priest = context.getPlayer1();
		Player warrior = context.getPlayer2();

		MinionCard eydisDarkbaneCard = (MinionCard) CardCatalogue.getCardById("minion_eydis_darkbane");
		Minion eydisDarkbane = playMinionCard(context, priest, eydisDarkbaneCard);

		Card testSpellCard = CardCatalogue.getCardById("spell_power_word_shield");
		context.getLogic().receiveCard(priest.getId(), testSpellCard);
		GameAction spellAction = testSpellCard.play();
		spellAction.setTarget(eydisDarkbane);
		context.getLogic().performGameAction(priest.getId(), spellAction);

		// priest casted a spell on Eydis - warrior should be wounded
		Assert.assertEquals(warrior.getHero().getHp(), warrior.getHero().getMaxHp() - 3);

		testSpellCard = CardCatalogue.getCardById("spell_shield_slam");
		context.getLogic().receiveCard(warrior.getId(), testSpellCard);
		spellAction = testSpellCard.play();
		spellAction.setTarget(eydisDarkbane);
		context.getLogic().performGameAction(warrior.getId(), spellAction);

		// warrior casted a spell on Eydis - nothing should happen
		Assert.assertEquals(warrior.getHero().getHp(), warrior.getHero().getMaxHp() - 3);
	}

	@Test
	public void testBetrayalOnBurlyRockjawTroggDeals5Damage() {
		GameContext context = createContext(HeroClass.PALADIN, HeroClass.ROGUE);
		Player paladin = context.getPlayer1();

		MinionCard adjacentMinionCard1 = new TestMinionCard(1, 5, 0);
		playMinionCard(context, paladin, adjacentMinionCard1);

		MinionCard targetMinionCard = (MinionCard) CardCatalogue.getCardById("minion_burly_rockjaw_trogg");
		Minion targetMinion = playMinionCard(context, paladin, targetMinionCard);

		MinionCard adjacentMinionCard2 = new TestMinionCard(1, 5, 0);
		playMinionCard(context, paladin, adjacentMinionCard2);

		context.getLogic().endTurn(paladin.getId());

		Assert.assertEquals(paladin.getMinions().size(), 3);

		Player rogue = context.getPlayer2();

		Card betrayal = CardCatalogue.getCardById("spell_betrayal");

		context.getLogic().receiveCard(rogue.getId(), betrayal);
		GameAction action = betrayal.play();
		action.setTarget(targetMinion);
		context.getLogic().performGameAction(rogue.getId(), action);

		Assert.assertEquals(paladin.getMinions().size(), 1);
	}

	@Test
	public void testRallyingBlade() {
		GameContext context = createContext(HeroClass.PALADIN, HeroClass.ROGUE);
		Player player = context.getPlayer1();
		MinionCard argentSquireCard = (MinionCard) CardCatalogue.getCardById("minion_argent_squire");
		Minion argentSquire = playMinionCard(context, player, argentSquireCard);
		Assert.assertEquals(argentSquire.getAttack(), 1);
		Assert.assertEquals(argentSquire.getHp(), 1);

		Card rallyingBladeCard = CardCatalogue.getCardById("weapon_rallying_blade");
		playCard(context, player, rallyingBladeCard);
		Assert.assertEquals(argentSquire.getAttack(), 2);
		Assert.assertEquals(argentSquire.getHp(), 2);
	}

	@Test
	public void testCurseOfRafaam() {
		GameContext context = createContext(HeroClass.WARRIOR, HeroClass.WARLOCK);

		Player player = context.getPlayer1();
		Card koboldGeomancerCard = CardCatalogue.getCardById("minion_kobold_geomancer");
		playCard(context, player, koboldGeomancerCard);
		context.endTurn();

		Player opponent = context.getPlayer2();
		Card curseOfRafaamCard = CardCatalogue.getCardById("spell_curse_of_rafaam");
		playCard(context, opponent, curseOfRafaamCard);
		context.endTurn();

		final int CURSE_OF_RAFAAM_DAMAGE = 2;
		// first player should take exactly 2 damage (NOT 3, because the spell
		// damage should not be applied)
		Assert.assertEquals(player.getHero().getHp(), player.getHero().getMaxHp() - CURSE_OF_RAFAAM_DAMAGE);

	}

	@Test
	public void testEmperorThaurissanEmptyHand() {
		GameContext context = createContext(HeroClass.WARRIOR, HeroClass.WARLOCK);

		Player player = context.getPlayer1();
		MinionCard emperorThaurissanCard = (MinionCard) CardCatalogue.getCardById("minion_emperor_thaurissan");
		Minion emperorThaurissan = playMinionCard(context, player, emperorThaurissanCard);
		for (Card card : player.getHand().toList()) {
			context.getLogic().removeCard(player.getId(), card);

		}
		Assert.assertTrue(player.getHand().isEmpty());
		context.endTurn();

		Player opponent = context.getPlayer2();
		Card assassinateCard = CardCatalogue.getCardById("spell_assassinate");
		playCardWithTarget(context, opponent, assassinateCard, emperorThaurissan);
		context.getLogic().receiveCard(player.getId(), CardCatalogue.getCardById("minion_chillwind_yeti"));
		context.endTurn();

		Card card = player.getHand().peekFirst();
		int modifiedCost = context.getLogic().getModifiedManaCost(player, card);
		System.out.println("Card [" + card.getName() + "] has baseManaCost of " + card.getBaseManaCost()
				+ " and current actual manacost of " + modifiedCost);
		Assert.assertEquals(card.getBaseManaCost(), modifiedCost);

	}

}
