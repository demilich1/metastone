package net.demilich.metastone.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.spells.SilenceSpell;
import net.demilich.metastone.game.spells.SwapAttackAndHpSpell;
import net.demilich.metastone.game.spells.TemporaryAttackSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class CardInteractionTests extends TestBase {

	@Test
	public void testAttackBuffStacking() {
		GameContext context = createContext(HeroClass.HUNTER, HeroClass.WARRIOR);
		Player hunter = context.getPlayer1();

		// summon Ghaz'rilla
		MinionCard gahzrillaCard = (MinionCard) CardCatalogue.getCardById("minion_gahzrilla");
		Minion gahzrilla = playMinionCard(context, hunter, gahzrillaCard);
		Assert.assertEquals(gahzrilla.getAttack(), 6);
		Assert.assertEquals(gahzrilla.getHp(), 9);

		// buff it with 'Abusive Sergeant' spell
		// This temporary Attack boost should be doubled and removed after the turn
		Card abusiveSergeant = CardCatalogue.getCardById("minion_abusive_sergeant");
		context.getLogic().receiveCard(hunter.getId(), abusiveSergeant);
		GameAction action = abusiveSergeant.play();
		action.setTarget(gahzrilla);
		context.getLogic().performGameAction(hunter.getId(), action);
		Assert.assertEquals(gahzrilla.getAttack(), 8);
		Assert.assertEquals(gahzrilla.getHp(), 9);

		context.getLogic().destroy((Actor) find(context, "minion_abusive_sergeant"));

		// buff it with 'Cruel Taskmaster' spell
		Card cruelTaskmasterCard = CardCatalogue.getCardById("minion_cruel_taskmaster");
		context.getLogic().receiveCard(hunter.getId(), cruelTaskmasterCard);
		action = cruelTaskmasterCard.play();
		action.setTarget(gahzrilla);
		context.getLogic().performGameAction(hunter.getId(), action);
		Assert.assertEquals(gahzrilla.getAttack(), 20);
		Assert.assertEquals(gahzrilla.getHp(), 8);

		context.getLogic().destroy((Actor) find(context, "minion_cruel_taskmaster"));

		// buff it again with 'Abusive Sergeant' spell
		abusiveSergeant = CardCatalogue.getCardById("minion_abusive_sergeant");
		context.getLogic().receiveCard(hunter.getId(), abusiveSergeant);
		action = abusiveSergeant.play();
		action.setTarget(gahzrilla);
		context.getLogic().performGameAction(hunter.getId(), action);
		Assert.assertEquals(gahzrilla.getAttack(), 22);
		Assert.assertEquals(gahzrilla.getHp(), 8);

		context.endTurn();
		context.endTurn();
		Assert.assertEquals(gahzrilla.getAttack(), 16);
		Assert.assertEquals(gahzrilla.getHp(), 8);
	}

	@Test
	public void testKnifeJugglerPlusStealth() {
		GameContext context = createContext(HeroClass.ROGUE, HeroClass.WARRIOR);
		Player player = context.getPlayer1();

		Minion knifeJuggler = playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_knife_juggler"));
		playCard(context, player, CardCatalogue.getCardById("spell_conceal"));
		// knife juggler should be stealthed
		Assert.assertTrue(knifeJuggler.hasAttribute(Attribute.STEALTH));
		// knife juggler should be unstealthed as soon as another minion is
		// played and his trigger fires
		playCard(context, player, new TestMinionCard(1, 1));
		Assert.assertFalse(knifeJuggler.hasAttribute(Attribute.STEALTH));
	}

	@Test
	public void testSilenceWithBuffs() {
		GameContext context = createContext(HeroClass.WARLOCK, HeroClass.WARRIOR);
		Player player = context.getPlayer1();

		// summon attack target
		context.endTurn();
		Player opponent = context.getPlayer2();
		playCard(context, opponent, new TestMinionCard(4, 4, 0));
		context.endTurn();

		// summon test minion
		player.setMana(10);
		TestMinionCard minionCard = new TestMinionCard(6, 6, 0);
		playCard(context, player, minionCard);

		Actor minion = getSingleMinion(player.getMinions());

		// buff test minion
		SpellCard buffSpellCard = (SpellCard) CardCatalogue.getCardById("spell_bananas");
		context.getLogic().receiveCard(player.getId(), buffSpellCard);
		GameAction action = buffSpellCard.play();
		action.setTarget(minion);
		context.getLogic().performGameAction(player.getId(), action);

		Assert.assertEquals(minion.getAttack(), 7);
		Assert.assertEquals(minion.getHp(), 7);

		// attack target to get test minion wounded
		attack(context, player, minion, getSingleMinion(opponent.getMinions()));
		Assert.assertEquals(minion.getAttack(), 7);
		Assert.assertEquals(minion.getHp(), 3);

		// swap hp and attack of wounded test minion
		SpellDesc swapHpAttackSpell = SwapAttackAndHpSpell.create(EntityReference.FRIENDLY_MINIONS);
		SpellCard swapSpellCard = new TestSpellCard(swapHpAttackSpell);
		buffSpellCard.setTargetRequirement(TargetSelection.NONE);
		playCard(context, player, swapSpellCard);
		Assert.assertEquals(minion.getAttack(), 3);
		Assert.assertEquals(minion.getHp(), 7);

		// silence minion and check if it regains original stats
		SpellDesc silenceSpell = SilenceSpell.create(EntityReference.FRIENDLY_MINIONS);
		SpellCard silenceSpellCard = new TestSpellCard(silenceSpell);
		silenceSpellCard.setTargetRequirement(TargetSelection.NONE);
		playCard(context, player, silenceSpellCard);
		Assert.assertEquals(minion.getAttack(), 6);
		Assert.assertEquals(minion.getHp(), 6);
	}

	@Test
	public void testSwapWithBuffs() {
		GameContext context = createContext(HeroClass.WARLOCK, HeroClass.WARRIOR);
		Player player = context.getPlayer1();

		// summon test minion
		player.setMana(10);
		TestMinionCard minionCard = new TestMinionCard(1, 3, 0);
		playCard(context, player, minionCard);

		// buff test minion with temporary buff
		SpellDesc buffSpell = TemporaryAttackSpell.create(EntityReference.FRIENDLY_MINIONS, +4);
		SpellCard buffSpellCard = new TestSpellCard(buffSpell);
		buffSpellCard.setTargetRequirement(TargetSelection.NONE);
		playCard(context, player, buffSpellCard);

		Actor minion = getSingleMinion(player.getMinions());
		Assert.assertEquals(minion.getAttack(), 5);
		Assert.assertEquals(minion.getHp(), 3);

		// swap hp and attack of wounded test minion
		SpellDesc swapHpAttackSpell = SwapAttackAndHpSpell.create(EntityReference.FRIENDLY_MINIONS);
		SpellCard swapSpellCard = new TestSpellCard(swapHpAttackSpell);
		buffSpellCard.setTargetRequirement(TargetSelection.NONE);
		playCard(context, player, swapSpellCard);
		Assert.assertEquals(minion.getAttack(), 3);
		Assert.assertEquals(minion.getHp(), 5);

		// end turn; temporary buff wears off, but stats should still be the
		// same
		context.endTurn();
		Assert.assertEquals(minion.getAttack(), 3);
		Assert.assertEquals(minion.getHp(), 5);
	}

	@Test
	public void testBloodsailRaider() {
		GameContext context = createContext(HeroClass.WARRIOR, HeroClass.MAGE);
		Player warrior = context.getPlayer1();
		warrior.setMana(10);

		playCard(context, warrior, CardCatalogue.getCardById("weapon_arcanite_reaper"));
		playCard(context, warrior, new TestMinionCard(2, 1, 0));

		Minion bloodsailRaider = playMinionCard(context, warrior, (MinionCard) CardCatalogue.getCardById("minion_bloodsail_raider"));
		Assert.assertEquals(bloodsailRaider.getAttack(), 7);
	}

	@Test
	public void testWildPyroPlusEquality() {
		GameContext context = createContext(HeroClass.PALADIN, HeroClass.WARRIOR);
		Player paladin = context.getPlayer1();
		playCard(context, paladin, new TestMinionCard(3, 2, 0));
		playCard(context, paladin, new TestMinionCard(4, 4, 0));
		context.getLogic().endTurn(paladin.getId());

		Player warrior = context.getPlayer2();
		playCard(context, warrior, new TestMinionCard(5, 5, 0));
		playCard(context, warrior, new TestMinionCard(1, 2, 0));
		playCard(context, warrior, new TestMinionCard(8, 8, 0));
		playCard(context, warrior, new TestMinionCard(2, 1, 0));
		context.getLogic().endTurn(warrior.getId());

		Assert.assertEquals(paladin.getMinions().size(), 2);
		Assert.assertEquals(warrior.getMinions().size(), 4);

		playCard(context, paladin, CardCatalogue.getCardById("minion_wild_pyromancer"));
		playCard(context, paladin, CardCatalogue.getCardById("spell_equality"));

		// wild pyromancer + equality should wipe the board if there no
		// deathrattles
		Assert.assertEquals(paladin.getMinions().size(), 0);
		Assert.assertEquals(warrior.getMinions().size(), 0);
	}
	
	@Test
	public void testLordJaraxxus() {
		GameContext context = createContext(HeroClass.WARLOCK, HeroClass.PALADIN);
		Player warlock = context.getPlayer1();
		Card jaraxxus = CardCatalogue.getCardById("minion_lord_jaraxxus");
		// first, just play Jaraxxus on an empty board
		playCard(context, warlock, jaraxxus);
		Assert.assertEquals(warlock.getHero().getRace(), Race.DEMON);
		Assert.assertEquals(warlock.getHero().getHp(), 15);
		Assert.assertNotNull(warlock.getHero().getWeapon());
		
		// start a new game
		context = createContext(HeroClass.WARLOCK, HeroClass.PALADIN);
		// opponent plays Repentance, which triggers on Lord Jaraxxus play
		Player paladin = context.getPlayer2();
		Card repentance = CardCatalogue.getCardById("secret_repentance");
		playCard(context, paladin, repentance);
		
		context.getLogic().endTurn(paladin.getId());
		
		warlock = context.getPlayer1();
		jaraxxus = CardCatalogue.getCardById("minion_lord_jaraxxus");
		playCard(context, warlock, jaraxxus);
		Assert.assertEquals(warlock.getHero().getRace(), Race.DEMON);
		// Jaraxxus should be affected by Repentance, bringing him down to 1 hp
		Assert.assertEquals(warlock.getHero().getHp(), 1);
		Assert.assertNotNull(warlock.getHero().getWeapon());
	}
	
	@Test
	public void testBlessingOfWisdomMindControl() {
		GameContext context = createContext(HeroClass.PALADIN, HeroClass.WARRIOR);
		Player player = context.getPlayer1();
		Player opponent = context.getPlayer2();
		
		int cardCount = player.getHand().getCount();
		
		Minion minion = playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_chillwind_yeti"));
		playCardWithTarget(context, player, CardCatalogue.getCardById("spell_blessing_of_wisdom"), minion);
		Assert.assertEquals(cardCount, player.getHand().getCount());
		
		attack(context, opponent, minion, opponent.getHero());
		Assert.assertEquals(player.getHand().getCount(), cardCount + 1);
		
		context.getLogic().mindControl(opponent, minion);
		attack(context, opponent, minion, player.getHero());
		Assert.assertEquals(player.getHand().getCount(), cardCount + 2);
	}
	
	@Test
	public void testImpFlamestrike() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARLOCK);
		Player player = context.getPlayer1();
		Player opponent = context.getPlayer2();
		
		context.endTurn();
		for (int i = 0; i < GameLogic.MAX_MINIONS; i++) {
			playMinionCard(context, opponent, (MinionCard) CardCatalogue.getCardById("minion_imp_gang_boss"));	
		}
		
		Assert.assertEquals(opponent.getMinions().size(), GameLogic.MAX_MINIONS);
		context.endTurn();
		
		playCard(context, player, CardCatalogue.getCardById("spell_flamestrike"));
		Assert.assertEquals(opponent.getMinions().size(), 0);
	}
	
	@Test
	public void testHarvestGolemFlamestrike() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARLOCK);
		Player player = context.getPlayer1();
		Player opponent = context.getPlayer2();
		
		context.endTurn();
		for (int i = 0; i < GameLogic.MAX_MINIONS; i++) {
			playMinionCard(context, opponent, (MinionCard) CardCatalogue.getCardById("minion_harvest_golem"));	
		}
		
		Assert.assertEquals(opponent.getMinions().size(), GameLogic.MAX_MINIONS);
		context.endTurn();
		
		playCard(context, player, CardCatalogue.getCardById("spell_flamestrike"));
		Assert.assertEquals(opponent.getMinions().size(), 7);
		
	}
	
	@Test
	public void testGrimPatrons() {
		GameContext context = createContext(HeroClass.PALADIN, HeroClass.WARRIOR);
		Player player = context.getPlayer1();
		Player opponent = context.getPlayer2();
		
		context.endTurn();
		for (int i = 0; i < 4; i++) {
			playMinionCard(context, opponent, (MinionCard) CardCatalogue.getCardById("minion_grim_patron"));	
		}
		
		Assert.assertEquals(opponent.getMinions().size(), 4);
		playCard(context, opponent, CardCatalogue.getCardById("spell_whirlwind"));
		Assert.assertEquals(opponent.getMinions().size(), 7);
		context.endTurn();
		
		playCard(context, player, CardCatalogue.getCardById("spell_consecration"));
		Assert.assertEquals(opponent.getMinions().size(), 3);
		
	}
	
	@Test
	public void testWobblingRunts() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Player player = context.getPlayer1();
		Player opponent = context.getPlayer2();
		
		context.endTurn();
		playMinionCard(context, opponent, (MinionCard) CardCatalogue.getCardById("minion_wobbling_runts"));
		for (int i = 0; i < GameLogic.MAX_MINIONS - 1; i++) {
			playMinionCard(context, opponent, (MinionCard) CardCatalogue.getCardById("minion_wisp"));	
		}
		
		Assert.assertEquals(opponent.getMinions().size(), GameLogic.MAX_MINIONS);
		context.endTurn();
		
		playCard(context, player, CardCatalogue.getCardById("minion_malygos"));
		playCard(context, player, CardCatalogue.getCardById("spell_flamestrike"));
		Assert.assertEquals(opponent.getMinions().size(), 3);
		
	}
	
	@Test
	public void testHauntedCreeperHarvestGolem() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Player player = context.getPlayer1();
		Player opponent = context.getPlayer2();
		
		context.endTurn();
		playMinionCard(context, opponent, (MinionCard) CardCatalogue.getCardById("minion_haunted_creeper"));
		playMinionCard(context, opponent, (MinionCard) CardCatalogue.getCardById("minion_harvest_golem"));
		Assert.assertEquals(opponent.getMinions().size(), 2);
		context.endTurn();
		
		playCard(context, player, CardCatalogue.getCardById("spell_flamestrike"));
		Assert.assertEquals(opponent.getMinions().size(), 3);
		final int HARVEST_GOLEM = 1;
		for (int i = 0; i < opponent.getMinions().size(); i++) {
			Minion minion = opponent.getMinions().get(i);
			if (i == HARVEST_GOLEM) {
				Assert.assertEquals(minion.getAttack(), 2);
				Assert.assertEquals(minion.getHp(), 1);
				Assert.assertEquals(minion.getRace(), Race.MECH);
			} else {
				Assert.assertEquals(minion.getAttack(), 1);
				Assert.assertEquals(minion.getHp(), 1);
			}
		}
	}
	
	@Test
	public void testImpGangBossConeOfCold() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Player player = context.getPlayer1();
		Player opponent = context.getPlayer2();
		
		context.endTurn();
		Minion firstYeti = playMinionCard(context, opponent, (MinionCard) CardCatalogue.getCardById("minion_chillwind_yeti"));
		Minion impGangBoss = playMinionCard(context, opponent, (MinionCard) CardCatalogue.getCardById("minion_imp_gang_boss"));
		Minion secondYeti = playMinionCard(context, opponent, (MinionCard) CardCatalogue.getCardById("minion_chillwind_yeti"));
		Assert.assertEquals(opponent.getMinions().size(), 3);
		context.endTurn();
		
		playCardWithTarget(context, player, CardCatalogue.getCardById("spell_cone_of_cold"), impGangBoss);
		Assert.assertEquals(opponent.getMinions().size(), 4);
		Assert.assertTrue(firstYeti.hasAttribute(Attribute.FROZEN));
		Assert.assertTrue(impGangBoss.hasAttribute(Attribute.FROZEN));
		Assert.assertFalse(secondYeti.hasAttribute(Attribute.FROZEN));
	}
	
	@Test
	public void testSummoningStonePrep() {
		GameContext context = createContext(HeroClass.ROGUE, HeroClass.WARRIOR);
		Player player = context.getPlayer1();
		
		playCard(context, player, CardCatalogue.getCardById("minion_summoning_stone"));
		playCard(context, player, CardCatalogue.getCardById("spell_preparation"));
		playCard(context, player, CardCatalogue.getCardById("secret_ice_block"));
		
		Assert.assertEquals(player.getMinions().size(), 3);
		for (Minion minion : player.getMinions()) {
			if (minion.getSourceCard().getCardId().equalsIgnoreCase("minion_summoning_stone")) {
				continue;
			}
			
			Assert.assertEquals(minion.getSourceCard().getBaseManaCost(), 0);
		}
	}
}

