import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.MinionAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.TheCoin;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Garrosh;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Jaina;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BasicTests extends TestBase {
	
	@Test
	public void testTheCoin() {
		GameContext context = createContext(new Jaina(), new Garrosh());
		Player mage = context.getPlayer1();
		Player warrior = context.getPlayer2();
		
		TheCoin theCoin = getTheCoin(mage.getHand());
		Assert.assertEquals(theCoin, null);
		theCoin = getTheCoin(warrior.getHand());
		Assert.assertNotEquals(theCoin, null);
	}
	
	private TheCoin getTheCoin(CardCollection<Card> cards) {
		for (Card card : cards) {
			if (card instanceof TheCoin) {
				return (TheCoin) card;
			}
		}
		return null;
	}

	@Test
	public void testSummon() {
		GameContext context = createContext(new Jaina(), new Garrosh());
		Player mage = context.getPlayer1();
		mage.getHand().removeAll();
		MinionCard devMonster = new DevMonster(1, 1);
		mage.getHand().add(devMonster);
		Assert.assertEquals(mage.getHand().getCount(), 1);
		context.getLogic().performGameAction(mage, devMonster.play());
		Assert.assertEquals(mage.getHand().isEmpty(), true);
		Entity minion = getSingleMinion(mage.getMinions());
		Assert.assertEquals(minion.getName(), devMonster.getName());
		Assert.assertEquals(minion.getAttack(), 1);
		Assert.assertEquals(minion.getHp(), 1);
		Assert.assertEquals(minion.isDead(), false);
	}

	@Test
	public void testMinionAttack() {
		GameContext context = createContext(new Jaina(), new Garrosh());
		Player mage = context.getPlayer1();
		mage.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		MinionCard minionCard1 = new DevMonster(5, 5);
		mage.getHand().add(minionCard1);
		context.getLogic().performGameAction(mage, minionCard1.play());
		
		MinionCard minionCard2 = new DevMonster(1, 1);
		mage.getHand().add(minionCard2);
		context.getLogic().performGameAction(warrior, minionCard2.play());
		
		Assert.assertEquals(mage.getMinions().size(), 1);
		Assert.assertEquals(warrior.getMinions().size(), 1);
		
		Entity attacker = getSingleMinion(mage.getMinions());
		Entity defender = getSingleMinion(warrior.getMinions());
		
		GameAction attackAction = new MinionAttackAction(attacker);
		attackAction.setTarget(defender);
		context.getLogic().performGameAction(mage, attackAction);
		
		Assert.assertEquals(attacker.getHp(), attacker.getMaxHp() - defender.getAttack());
		Assert.assertEquals(defender.getHp(), defender.getMaxHp() - attacker.getAttack());
		Assert.assertEquals(defender.isDead(), true);
		
		Assert.assertEquals(mage.getMinions().size(), 1);
		Assert.assertEquals(warrior.getMinions().size(), 0);
	}
	
	@Test
	public void testBattlecry() { 
		GameContext context = createContext(new Jaina(), new Garrosh());
		Player mage = context.getPlayer1();
		mage.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		DevMonster devMonster = new DevMonster(3, 3);
		Battlecry testBattlecry = Battlecry.createBattlecry(new SingleTargetDamageSpell(3), TargetSelection.ENEMY_HERO);
		testBattlecry.setTarget(warrior.getHero());
		devMonster.getMinion().setTag(GameTag.BATTLECRY, testBattlecry);
		mage.getHand().add(devMonster);
		context.getLogic().performGameAction(mage, devMonster.play());
		
		Assert.assertEquals(warrior.getHero().getHp(), warrior.getHero().getMaxHp() - 3);
	}

}
