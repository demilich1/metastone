import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.MinionAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.Shieldbearer;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.Wisp;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Garrosh;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Jaina;

import org.testng.Assert;
import org.testng.annotations.Test;


public class TargetingTests extends TestBase {
	
	@Test
	public void testTaunt() {
		GameContext context = createContext(new Jaina(), new Garrosh());
		Player mage = context.getPlayer1();
		Player victim = context.getPlayer2();
		
		MinionCard tauntCard = new Shieldbearer();
		victim.getHand().add(tauntCard);
		
		MinionCard attackerCard = new Wisp();
		mage.getHand().add(attackerCard);
		
		context.getLogic().performGameAction(victim, tauntCard.play());
		context.getLogic().performGameAction(mage, attackerCard.play());
		
		Entity attacker = getSingleMinion(mage.getMinions());
		Entity defender = getSingleMinion(victim.getMinions());
		Assert.assertEquals(defender.hasTag(GameTag.TAUNT), true);
		
		List<Entity> validTargets;
		
		GameAction attackAction = new MinionAttackAction(attacker);
		validTargets = context.getLogic().getValidTargets(mage, attackAction);
		Assert.assertEquals(validTargets.size(), 1);
		
		GameAction fireblast = mage.getHero().getHeroPower().play();
		validTargets = context.getLogic().getValidTargets(mage, fireblast);
		Assert.assertEquals(validTargets.size(), 4);
		
		defender.removeTag(GameTag.TAUNT);
		
		validTargets = context.getLogic().getValidTargets(mage, attackAction);
		Assert.assertEquals(validTargets.size(), 2);
		
		validTargets = context.getLogic().getValidTargets(mage, fireblast);
		Assert.assertEquals(validTargets.size(), 4);
		
		//TODO: add stealth + taunt test
		
	}

}
