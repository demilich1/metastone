package net.demilich.metastone.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.logic.GameLogic;

public class PoisonSeedsTests extends TestBase {

	@Test
	public void testPoisonSeeds() {
		GameContext context = createContext(HeroClass.DRUID, HeroClass.ROGUE);
		Player druid = context.getPlayer1();
		Player rogue = context.getPlayer2();
		MinionCard chillwindYeti = (MinionCard) CardCatalogue.getCardById("minion_chillwind_yeti");

		for (int i = 0; i < GameLogic.MAX_MINIONS; i++) {
			playMinionCard(context, druid, chillwindYeti);
		}

		MinionCard nerubianEgg = (MinionCard) CardCatalogue.getCardById("minion_nerubian_egg");
		for (int i = 0; i < 3; i++) {
			playMinionCard(context, rogue, nerubianEgg);
		}

		Assert.assertEquals(druid.getMinions().size(), GameLogic.MAX_MINIONS);
		Assert.assertEquals(rogue.getMinions().size(), 3);

		SpellCard poisonSeeds = (SpellCard) CardCatalogue.getCardById("spell_poison_seeds");
		playCard(context, druid, poisonSeeds);

		Assert.assertEquals(druid.getMinions().size(), GameLogic.MAX_MINIONS);
		Assert.assertEquals(rogue.getMinions().size(), 6);
		for (Minion minion : druid.getMinions()) {
			Assert.assertEquals(minion.getSourceCard().getCardId(), "token_treant");
		}
	}

	@Test
	public void testPoisonSeedsAuchenai() {
		GameContext context = createContext(HeroClass.DRUID, HeroClass.PRIEST);
		Player druid = context.getPlayer1();
		Player priest = context.getPlayer2();

		MinionCard zombieChow = (MinionCard) CardCatalogue.getCardById("minion_zombie_chow");
		playMinionCard(context, priest, zombieChow);
		playMinionCard(context, priest, zombieChow);

		MinionCard auchenaiSoulpriest = (MinionCard) CardCatalogue.getCardById("minion_auchenai_soulpriest");
		playMinionCard(context, priest, auchenaiSoulpriest);

		Card pyroblast = CardCatalogue.getCardById("spell_pyroblast");
		context.getLogic().receiveCard(druid.getId(), pyroblast);
		GameAction gameAction = pyroblast.play();
		gameAction.setTarget(druid.getHero());
		context.getLogic().performGameAction(druid.getId(), gameAction);

		Assert.assertEquals(druid.getHero().getHp(), GameLogic.MAX_HERO_HP - 10);

		SpellCard poisonSeeds = (SpellCard) CardCatalogue.getCardById("spell_poison_seeds");
		playCard(context, druid, poisonSeeds);

		Assert.assertEquals(druid.getHero().getHp(), GameLogic.MAX_HERO_HP);
	}

	@Test
	public void testPoisonSeedsHauntedCreeper() {
		GameContext context = createContext(HeroClass.DRUID, HeroClass.ROGUE);
		Player druid = context.getPlayer1();
		MinionCard hauntedCreeper = (MinionCard) CardCatalogue.getCardById("minion_haunted_creeper");

		for (int i = 0; i < 4; i++) {
			playMinionCard(context, druid, hauntedCreeper);
		}
		Assert.assertEquals(druid.getMinions().size(), 4);

		SpellCard poisonSeeds = (SpellCard) CardCatalogue.getCardById("spell_poison_seeds");
		playCard(context, druid, poisonSeeds);

		Assert.assertEquals(druid.getMinions().size(), GameLogic.MAX_MINIONS);

		for (Minion minion : druid.getMinions()) {
			Assert.assertEquals(minion.getSourceCard().getCardId(), "token_spectral_spider");
		}
	}

}
