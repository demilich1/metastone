package net.demilich.metastone.tests.allcards;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.tests.TestBase;

public class ClassicNeutralCards extends TestBase {

	@Test
	public void testGrimscaleOracle() {
		GameContext context = createContext(HeroClass.WARRIOR, HeroClass.SHAMAN);
		Player player = context.getPlayer1();
		Player opponent = context.getPlayer2();

		playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_murloc_tidehunter"));
		playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_bluegill_warrior"));

		Assert.assertEquals(player.getMinions().size(), 3);
		context.endTurn();

		playMinionCard(context, opponent, (MinionCard) CardCatalogue.getCardById("minion_grimscale_oracle"));
		playMinionCard(context, opponent, (MinionCard) CardCatalogue.getCardById("minion_murloc_raider"));

		final int MURLOC_TIDEHUNTER = 0;
		final int MURLOC_SCOUT = 1;
		final int BLUEGILL_WARRIOR = 2;
		for (int i = 0; i < player.getMinions().size(); i++) {
			Minion minion = player.getMinions().get(i);
			switch (i) {
			case MURLOC_TIDEHUNTER:
			case BLUEGILL_WARRIOR:
				Assert.assertEquals(minion.getAttack(), 3);
				Assert.assertEquals(minion.getHp(), 1);
				break;
			case MURLOC_SCOUT:
				Assert.assertEquals(minion.getAttack(), 2);
				Assert.assertEquals(minion.getHp(), 1);
				break;
			}
		}

		final int GRIMSCALE_ORACLE = 0;
		final int MURLOC_RAIDER = 1;
		for (int i = 0; i < opponent.getMinions().size(); i++) {
			Minion minion = opponent.getMinions().get(i);
			switch (i) {
			case GRIMSCALE_ORACLE:
				Assert.assertEquals(minion.getAttack(), 1);
				Assert.assertEquals(minion.getHp(), 1);
				break;
			case MURLOC_RAIDER:
				Assert.assertEquals(minion.getAttack(), 3);
				Assert.assertEquals(minion.getHp(), 1);
				break;
			}
		}
	}

	@Test
	public void testElvenArcher() {
		GameContext context = createContext(HeroClass.WARRIOR, HeroClass.SHAMAN);
		Player player = context.getPlayer1();
		Player opponent = context.getPlayer2();

		Assert.assertEquals(opponent.getHero().getHp(), GameLogic.MAX_HERO_HP);
		MinionCard elvenArcherCard = (MinionCard) CardCatalogue.getCardById("minion_elven_archer");
		playCardWithTarget(context, player, elvenArcherCard, opponent.getHero());
		Assert.assertEquals(opponent.getHero().getHp(), GameLogic.MAX_HERO_HP - 1);
	}

	@Test
	public void testNoviceEngineer() {
		GameContext context = createContext(HeroClass.WARRIOR, HeroClass.SHAMAN);
		Player player = context.getPlayer1();

		int cardCount = player.getHand().getCount();
		Assert.assertEquals(player.getHand().getCount(), cardCount);
		Card noviceEngineerCard = CardCatalogue.getCardById("minion_novice_engineer");
		playCard(context, player, noviceEngineerCard);
		Assert.assertEquals(player.getHand().getCount(), cardCount + 1);
	}

	@Test
	public void testKoboldGeomancer() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Player player = context.getPlayer1();
		Player opponent = context.getPlayer2();

		Assert.assertEquals(opponent.getHero().getHp(), GameLogic.MAX_HERO_HP);
		playCard(context, player, CardCatalogue.getCardById("spell_arcane_missiles"));
		Assert.assertEquals(opponent.getHero().getHp(), GameLogic.MAX_HERO_HP - 3);
		playCardWithTarget(context, player, CardCatalogue.getCardById("spell_fireball"), opponent.getHero());
		Assert.assertEquals(opponent.getHero().getHp(), GameLogic.MAX_HERO_HP - 3 - 6);

		playCard(context, player, CardCatalogue.getCardById("minion_kobold_geomancer"));

		playCard(context, player, CardCatalogue.getCardById("spell_arcane_missiles"));
		Assert.assertEquals(opponent.getHero().getHp(), GameLogic.MAX_HERO_HP - 3 - 6 - 4);
		playCardWithTarget(context, player, CardCatalogue.getCardById("spell_fireball"), opponent.getHero());
		Assert.assertEquals(opponent.getHero().getHp(), GameLogic.MAX_HERO_HP - 3 - 6 - 4 - 7);
	}

	@Test
	public void testAcidicSwampOoze() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Player player = context.getPlayer1();
		Player opponent = context.getPlayer2();

		context.endTurn();
		playCard(context, opponent, CardCatalogue.getCardById("weapon_fiery_war_axe"));
		Assert.assertNotNull(opponent.getHero().getWeapon());
		context.endTurn();

		playCard(context, player, CardCatalogue.getCardById("minion_acidic_swamp_ooze"));
		Assert.assertNull(opponent.getHero().getWeapon());
	}

}
