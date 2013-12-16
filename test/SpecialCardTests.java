import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.MinionAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.GurubashiBerserker;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.OasisSnapjaw;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Garrosh;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Jaina;

import org.testng.Assert;
import org.testng.annotations.Test;


public class SpecialCardTests extends TestBase {
	
	@Test
	public void testGurubashiBerserker() {
		GameContext context = createContext(new Jaina(), new Garrosh());
		Player mage = context.getPlayer1();
		mage.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		MinionCard gurubashiBerserkerCard = new GurubashiBerserker();
		warrior.getHand().add(gurubashiBerserkerCard);
		context.getLogic().performGameAction(warrior, gurubashiBerserkerCard.play());
		
		MinionCard oasisSnapjawCard = new OasisSnapjaw();
		mage.getHand().add(oasisSnapjawCard);
		context.getLogic().performGameAction(mage, oasisSnapjawCard.play());
		
		Entity attacker = getSingleMinion(mage.getMinions());
		Entity defender = getSingleMinion(warrior.getMinions());
		
		// Gurubashi Berserker should start with just his base attack
		Assert.assertEquals(defender.getAttack(), GurubashiBerserker.BASE_ATTACK);
		
		// first attack, Gurubashi Berserker should have increased attack
		GameAction attackAction = new MinionAttackAction(attacker);
		attackAction.setTarget(defender);
		context.getLogic().performGameAction(mage, attackAction);
		
		Assert.assertEquals(attacker.getHp(), attacker.getMaxHp() - GurubashiBerserker.BASE_ATTACK);
		Assert.assertEquals(defender.getHp(), defender.getMaxHp() - attacker.getAttack());
		Assert.assertEquals(defender.getAttack(), GurubashiBerserker.BASE_ATTACK + GurubashiBerserker.ATTACK_BONUS);
		
		// second attack, Gurubashi Berserker should become even stronger
		context.getLogic().performGameAction(mage, attackAction);
		Assert.assertEquals(attacker.getHp(), attacker.getMaxHp() - 2 * GurubashiBerserker.BASE_ATTACK - GurubashiBerserker.ATTACK_BONUS);
		Assert.assertEquals(defender.getHp(), defender.getMaxHp() - 2 * attacker.getAttack());
		Assert.assertEquals(defender.getAttack(), GurubashiBerserker.BASE_ATTACK + 2 * GurubashiBerserker.ATTACK_BONUS);
	}
	
}
