package net.demilich.metastone.tests;
import org.testng.Assert;
import org.testng.annotations.Test;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.heroes.HeroClass;


public class SecretTest extends TestBase {
	
	@Test
	public void testKillingStopsAttack() {
		DebugContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Player mage = context.getPlayer1();
		mage.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		final int SECRET_DAMAGE = 2;
		playCard(context, mage, new TestSecretCard(SECRET_DAMAGE));
		playCard(context, warrior, new TestMinionCard(2, 3));
		
		context.setActivePlayer(warrior.getId());
		Actor minion = getSingleMinion(warrior.getMinions());
		attack(context, warrior, minion, mage.getHero());
		Assert.assertEquals(mage.getHero().getHp(), mage.getHero().getMaxHp() - minion.getAttack());
		Assert.assertEquals(minion.getHp(), minion.getMaxHp() - SECRET_DAMAGE);
		
		playCard(context, mage, new TestSecretCard(SECRET_DAMAGE));
		attack(context, warrior, minion, mage.getHero());
		Assert.assertTrue(minion.isDead());
		Assert.assertEquals(mage.getHero().getHp(), mage.getHero().getMaxHp() - minion.getAttack());
	}
	
	@Test
	public void testNewSpellTarget() {
		DebugContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Player mage = context.getPlayer1();
		mage.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		int fullHp = 10;
		playCard(context, warrior, new TestMinionCard(2, fullHp));
		
		Actor minion = getSingleMinion(warrior.getMinions());
		context.getLogic().endTurn(mage.getId());

		for (int i = 0; i < 2; i++) {
			playCard(context, mage, CardCatalogue.getCardById("secret_spellbender"));
			Assert.assertEquals(mage.getSecrets().size(), 1);
			
			Card testSpellCard = CardCatalogue.getCardById("spell_frostbolt");
			context.getLogic().receiveCard(warrior.getId(), testSpellCard);
			GameAction spellAttackAction = testSpellCard.play();
			spellAttackAction.setTarget(minion);
			
			context.setActivePlayer(warrior.getId());
			context.getLogic().performGameAction(warrior.getId(), spellAttackAction);
			
			Assert.assertEquals(minion.getHp(), fullHp);
			Assert.assertEquals(warrior.getMinions().size(), 1);
			
			attack(context, warrior, minion, mage.getHero());
		}
		
	}
	
	@Test
	public void testPlayOnlyOnce() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Player mage = context.getPlayer1();
		mage.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		SecretCard secretCard = new TestSecretCard();
		context.getLogic().receiveCard(mage.getId(), secretCard);
		Assert.assertTrue(context.getLogic().canPlaySecret(mage, secretCard));
		context.getLogic().performGameAction(mage.getId(), secretCard.play());
		
		SecretCard secretCard2 = new TestSecretCard();
		context.getLogic().receiveCard(mage.getId(), secretCard2);
		Assert.assertFalse(context.getLogic().canPlaySecret(mage, secretCard2));
		
		SecretCard otherSecret = (SecretCard) CardCatalogue.getCardById("secret_explosive_trap");
		context.getLogic().receiveCard(mage.getId(), otherSecret);
		Assert.assertTrue(context.getLogic().canPlaySecret(mage, otherSecret));
	}


}
