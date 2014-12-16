import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PhysicalAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.Shieldbearer;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.Wisp;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;

import org.testng.Assert;
import org.testng.annotations.Test;


public class TargetingTests extends TestBase {
	
	@Test
	public void testTaunt() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Player mage = context.getPlayer1();
		Player victim = context.getPlayer2();
		
		MinionCard tauntCard = new Shieldbearer();
		context.getLogic().receiveCard(victim.getId(), tauntCard);
		
		MinionCard attackerCard = new Wisp();
		context.getLogic().receiveCard(mage.getId(), attackerCard);
		
		context.getLogic().performGameAction(victim.getId(), tauntCard.play());
		context.getLogic().performGameAction(mage.getId(), attackerCard.play());
		
		Entity attacker = getSingleMinion(mage.getMinions());
		Entity defender = getSingleMinion(victim.getMinions());
		Assert.assertEquals(defender.hasStatus(GameTag.TAUNT), true);
		
		List<Entity> validTargets;
		
		GameAction attackAction = new PhysicalAttackAction(attacker.getReference());
		validTargets = context.getLogic().getValidTargets(mage.getId(), attackAction);
		Assert.assertEquals(validTargets.size(), 1);
		
		GameAction fireblast = mage.getHero().getHeroPower().play();
		validTargets = context.getLogic().getValidTargets(mage.getId(), fireblast);
		Assert.assertEquals(validTargets.size(), 4);
		
		defender.removeTag(GameTag.TAUNT);
		
		validTargets = context.getLogic().getValidTargets(mage.getId(), attackAction);
		Assert.assertEquals(validTargets.size(), 2);
		
		validTargets = context.getLogic().getValidTargets(mage.getId(), fireblast);
		Assert.assertEquals(validTargets.size(), 4);
		
		// taunt should be ignored when the minion is stealthed
		defender.setTag(GameTag.TAUNT);
		defender.setTag(GameTag.STEALTHED);
		validTargets = context.getLogic().getValidTargets(mage.getId(), attackAction);
		Assert.assertEquals(validTargets.size(), 1);
		Assert.assertEquals(validTargets.get(0).getEntityType(), EntityType.HERO);
		
	}

}
