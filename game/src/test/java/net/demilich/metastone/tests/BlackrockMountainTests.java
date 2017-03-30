package net.demilich.metastone.tests;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import org.testng.Assert;
import org.testng.annotations.Test;


public class BlackrockMountainTests extends BasicTests {

	@Test
	public void testAxeFlinger() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Player player = context.getPlayer1();

		playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_axe_flinger"));
		playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_axe_flinger"));
		
		context.getLogic().endTurn(player.getId());
		Player opponent = context.getOpponent(player);
		
		SpellCard damageCard = new TestSpellCard(DamageSpell.create(EntityReference.ENEMY_CHARACTERS, 1));
		playCard(context, opponent, damageCard);
		Assert.assertEquals(player.getHero().getHp(), player.getHero().getMaxHp() - 1);
		Assert.assertEquals(opponent.getHero().getHp(), opponent.getHero().getMaxHp() - 4);
	}
	
	@Test
	public void testBlackwingCorruptor() {
		GameContext context = createContext(HeroClass.DRUID, HeroClass.HUNTER);
		Player player = context.getPlayer1();
		player.getHand().removeAll();
		
		TestBehaviour behaviour = (TestBehaviour) player.getBehaviour();
		behaviour.setTargetPreference(player.getHero().getReference());
		
		playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_blackwing_corruptor"));
		Assert.assertEquals(player.getHero().getHp(), player.getHero().getMaxHp());
		
		context.getLogic().receiveCard(player.getId(), CardCatalogue.getCardById("minion_azure_drake"));
		playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_blackwing_corruptor"));
		Assert.assertEquals(player.getHero().getHp(), player.getHero().getMaxHp() - 3);
	}
	
	@Test
	public void testBlackwingTechnician() {
		GameContext context = createContext(HeroClass.DRUID, HeroClass.HUNTER);
		Player player = context.getPlayer1();
		context.getLogic().removeAllCards(player.getId());
		
		Minion blackwingTechnician = playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_blackwing_technician"));
		Assert.assertEquals(blackwingTechnician.getHp(), blackwingTechnician.getBaseHp());
		Assert.assertEquals(blackwingTechnician.getAttack(), blackwingTechnician.getBaseAttack());
		
		context.getLogic().receiveCard(player.getId(), CardCatalogue.getCardById("minion_azure_drake"));
		blackwingTechnician = playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_blackwing_technician"));
		Assert.assertEquals(blackwingTechnician.getHp(), blackwingTechnician.getBaseHp() + 1);
		Assert.assertEquals(blackwingTechnician.getAttack(), blackwingTechnician.getBaseAttack() + 1);
	}
	
	@Test
	public void testChromaggus() {
		GameContext context = createContext(HeroClass.DRUID, HeroClass.HUNTER);
		Player player = context.getPlayer1();
		player.getHand().removeAll();
		
		Assert.assertEquals(player.getHand().getCount(), 0);
		
		playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_chromaggus"));
		context.getLogic().drawCard(player.getId(), player.getHero());
		Assert.assertEquals(player.getHand().getCount(), 2);
		
		player.getHand().removeAll();
		
		Assert.assertEquals(player.getHand().getCount(), 0);
		
		playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_chromaggus"));
		context.getLogic().drawCard(player.getId(), player.getHero());
		Assert.assertEquals(player.getHand().getCount(), 3);
	}
	
	@Test
	public void testCoreRager() {
		GameContext context = createContext(HeroClass.DRUID, HeroClass.HUNTER);
		Player player = context.getPlayer1();
		player.getHand().removeAll();
		
		Minion coreRager = playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_core_rager"));
		Assert.assertEquals(coreRager.getAttack(), coreRager.getBaseAttack() + 3);
		Assert.assertEquals(coreRager.getHp(), coreRager.getBaseHp() + 3);
		
		context.getLogic().drawCard(player.getId(), player.getHero());
		
		coreRager = playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_core_rager"));
		Assert.assertEquals(coreRager.getAttack(), coreRager.getBaseAttack());
		Assert.assertEquals(coreRager.getHp(), coreRager.getBaseHp());
	}
	
	@Test
	public void testDarkIronSkulker() {
		GameContext context = createContext(HeroClass.DRUID, HeroClass.HUNTER);
		Player player = context.getPlayer1();
		Player opponent = context.getOpponent(player);
		
		Minion testMinion1 = playMinionCard(context, player, new TestMinionCard(3, 3, 0));
		Minion injuredBlademaster = playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_injured_blademaster"));
		Minion testMinion2 = playMinionCard(context, player, new TestMinionCard(3, 3, 0));
		Assert.assertEquals(testMinion1.getHp(), testMinion1.getMaxHp());
		Assert.assertEquals(injuredBlademaster.getHp(), injuredBlademaster.getMaxHp() - 4);
		Assert.assertEquals(testMinion2.getHp(), testMinion2.getMaxHp());
		
		context.getLogic().endTurn(player.getId());
		
		Minion testMinionOpponent = playMinionCard(context, opponent, new TestMinionCard(3, 3, 0));
		Minion injuredBlademasterOpponent = playMinionCard(context, opponent, (MinionCard) CardCatalogue.getCardById("minion_injured_blademaster"));
		Assert.assertEquals(testMinionOpponent.getHp(), testMinionOpponent.getMaxHp());
		Assert.assertEquals(injuredBlademasterOpponent.getHp(), injuredBlademasterOpponent.getMaxHp() - 4);
		
		Minion darkIronSkulker = playMinionCard(context, opponent, (MinionCard) CardCatalogue.getCardById("minion_dark_iron_skulker"));
		Assert.assertEquals(darkIronSkulker.getHp(), darkIronSkulker.getMaxHp());
		
		Assert.assertEquals(testMinionOpponent.getHp(), testMinionOpponent.getMaxHp());
		Assert.assertEquals(injuredBlademasterOpponent.getHp(), injuredBlademasterOpponent.getMaxHp() - 4);
		
		Assert.assertEquals(testMinion1.getHp(), testMinion1.getMaxHp() - 2);
		Assert.assertEquals(injuredBlademaster.getHp(), injuredBlademaster.getMaxHp() - 4);
		Assert.assertEquals(testMinion2.getHp(), testMinion2.getMaxHp() - 2);
	}
	
	@Test
	public void testDragonConsort() {
		GameContext context = createContext(HeroClass.DRUID, HeroClass.HUNTER);
		Player player = context.getPlayer1();
		
		final int MANA_REDUCTION = 2;
		
		MinionCard dragonConsort = (MinionCard) CardCatalogue.getCardById("minion_dragon_consort");
		context.getLogic().receiveCard(player.getId(), dragonConsort);
		Assert.assertEquals(dragonConsort.getManaCost(context, player), dragonConsort.getBaseManaCost());

		playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_dragon_consort"));
		Assert.assertEquals(context.getLogic().getModifiedManaCost(player, dragonConsort), dragonConsort.getBaseManaCost() - MANA_REDUCTION);
	}
	
	@Test
	public void testDragonEgg() {
		GameContext context = createContext(HeroClass.DRUID, HeroClass.HUNTER);
		Player player = context.getPlayer1();
		
		final String TOKEN = "token_black_whelp";
		
		Minion dragonEgg = playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_dragon_egg"));
		Assert.assertEquals(getSummonedMinion(player.getMinions()), dragonEgg);

		playCardWithTarget(context, player, CardCatalogue.getCardById("spell_fireball"), dragonEgg);
		Assert.assertEquals(getSummonedMinion(player.getMinions()).getSourceCard().getCardId(), TOKEN);
		
	}
	
	@Test
	public void testDragonkinSorceror() {
		GameContext context = createContext(HeroClass.DRUID, HeroClass.HUNTER);
		Player player = context.getPlayer1();
		
		final int ATTACK_BONUS = 1;
		final int HP_BONUS = 1;
		
		Minion dragonkin1 = playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_dragonkin_sorcerer"));
		Minion dragonkin2 = playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_dragonkin_sorcerer"));
		Assert.assertEquals(dragonkin1.getAttack(), dragonkin2.getAttack());
		Assert.assertEquals(dragonkin1.getHp(), dragonkin2.getHp());
		
		playCardWithTarget(context, player, CardCatalogue.getCardById("spell_gang_up"), dragonkin1);
		Assert.assertEquals(dragonkin1.getAttack(), dragonkin2.getAttack() + ATTACK_BONUS);
		Assert.assertEquals(dragonkin1.getHp(), dragonkin2.getHp() + HP_BONUS);
	}
	
	@Test
	public void testDrakonidCrusher() {
		GameContext context = createContext(HeroClass.DRUID, HeroClass.HUNTER);
		Player player = context.getPlayer1();
		Player opponent = context.getPlayer2();
		
		final int ATTACK_BONUS = 3;
		final int HP_BONUS = 3;
		
		Minion drakonid = playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_drakonid_crusher"));
		Assert.assertEquals(drakonid.getAttack(), drakonid.getBaseAttack());
		Assert.assertEquals(drakonid.getHp(), drakonid.getBaseHp());
		
		opponent.getHero().setHp(15);
		
		drakonid = playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_drakonid_crusher"));
		Assert.assertEquals(drakonid.getAttack(), drakonid.getBaseAttack() + ATTACK_BONUS);
		Assert.assertEquals(drakonid.getHp(), drakonid.getBaseHp() + HP_BONUS);
	}
}
