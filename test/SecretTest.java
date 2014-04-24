import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Garrosh;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Jaina;

import org.testng.Assert;
import org.testng.annotations.Test;


public class SecretTest extends TestBase {
	
	@Test
	public void testPlayOnlyOnce() {
		GameContext context = createContext(new Jaina(), new Garrosh());
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
		
		SecretCard otherSecret = new SecretCard("AnotherSecret", Rarity.COMMON, HeroClass.ANY, 0);
		context.getLogic().receiveCard(mage.getId(), otherSecret);
		Assert.assertTrue(context.getLogic().canPlaySecret(mage, otherSecret));
	}
	
	@Test
	public void testKillingStopsAttack() {
		GameContext context = createContext(new Jaina(), new Garrosh());
		Player mage = context.getPlayer1();
		mage.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		final int SECRET_DAMAGE = 2;
		playCard(context, mage, new TestSecretCard(SECRET_DAMAGE));
		playCard(context, warrior, new TestMinionCard(2, 3));
		
		Actor minion = getSingleMinion(warrior.getMinions());
		attack(context, warrior, minion, mage.getHero());
		Assert.assertEquals(mage.getHero().getHp(), mage.getHero().getMaxHp() - minion.getAttack());
		Assert.assertEquals(minion.getHp(), minion.getMaxHp() - SECRET_DAMAGE);
		
		playCard(context, mage, new TestSecretCard(SECRET_DAMAGE));
		attack(context, warrior, minion, mage.getHero());
		Assert.assertTrue(minion.isDead());
		Assert.assertEquals(mage.getHero().getHp(), mage.getHero().getMaxHp() - minion.getAttack());
	}


}
