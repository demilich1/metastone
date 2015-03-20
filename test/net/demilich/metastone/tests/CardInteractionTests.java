package net.demilich.metastone.tests;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.cards.concrete.neutral.BloodsailRaider;
import net.demilich.metastone.game.cards.concrete.neutral.KnifeJuggler;
import net.demilich.metastone.game.cards.concrete.neutral.MurlocRaider;
import net.demilich.metastone.game.cards.concrete.neutral.WildPyromancer;
import net.demilich.metastone.game.cards.concrete.paladin.Equality;
import net.demilich.metastone.game.cards.concrete.rogue.Conceal;
import net.demilich.metastone.game.cards.concrete.warrior.ArcaniteReaper;
import net.demilich.metastone.game.cards.concrete.warrior.WarsongCommander;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.SilenceSpell;
import net.demilich.metastone.game.spells.TemporaryAttackSpell;
import net.demilich.metastone.game.spells.custom.SwapAttackAndHpSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CardInteractionTests extends TestBase {

	@Test
	public void testKnifeJugglerPlusStealth() {
		GameContext context = createContext(HeroClass.ROGUE, HeroClass.WARRIOR);
		Player player = context.getPlayer1();
		
		Minion knifeJuggler = playMinionCard(context, player, new KnifeJuggler());
		playCard(context, player, new Conceal());
		// knife juggler should be stealthed
		Assert.assertTrue(knifeJuggler.hasStatus(GameTag.STEALTHED));
		// knife juggler should be unstealthed as soon as another minion is played and his trigger fires
		playCard(context, player, new TestMinionCard(1, 1));
		Assert.assertFalse(knifeJuggler.hasStatus(GameTag.STEALTHED));
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

		// buff test minion
		SpellDesc buffSpell = BuffSpell.create(EntityReference.FRIENDLY_MINIONS, 1, 1);
		SpellCard buffSpellCard = new TestSpellCard(buffSpell);
		buffSpellCard.setTargetRequirement(TargetSelection.NONE);
		playCard(context, player, buffSpellCard);

		Actor minion = getSingleMinion(player.getMinions());
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
	public void testWarriorCards() {
		GameContext context = createContext(HeroClass.WARRIOR, HeroClass.MAGE);
		Player warrior = context.getPlayer1();
		warrior.setMana(10);

		playCard(context, warrior, new ArcaniteReaper());
		playCard(context, warrior, new WarsongCommander());
		playCard(context, warrior, new MurlocRaider());

		Minion bloodsailRaider = playMinionCard(context, warrior, new BloodsailRaider());
		Assert.assertTrue(bloodsailRaider.hasStatus(GameTag.CHARGE));
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

		playCard(context, paladin, new WildPyromancer());
		playCard(context, paladin, new Equality());

		// wild pyromancer + equality should wipe the board if there no
		// deathrattles
		Assert.assertEquals(paladin.getMinions().size(), 0);
		Assert.assertEquals(warrior.getMinions().size(), 0);
	}

}
