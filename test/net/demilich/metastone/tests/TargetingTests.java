package net.demilich.metastone.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.actions.PhysicalAttackAction;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.heroes.HeroClass;

public class TargetingTests extends TestBase {

	@Test
	public void testTaunt() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARRIOR);
		Player mage = context.getPlayer1();
		Player victim = context.getPlayer2();

		MinionCard tauntCard = (MinionCard) CardCatalogue.getCardById("minion_shieldbearer");
		context.getLogic().receiveCard(victim.getId(), tauntCard);

		MinionCard attackerCard = new TestMinionCard(1, 1, 0);
		context.getLogic().receiveCard(mage.getId(), attackerCard);

		context.getLogic().performGameAction(victim.getId(), tauntCard.play());
		context.getLogic().performGameAction(mage.getId(), attackerCard.play());

		Entity attacker = getSingleMinion(mage.getMinions());
		Entity defender = getSingleMinion(victim.getMinions());
		Assert.assertEquals(defender.hasAttribute(Attribute.TAUNT), true);

		List<Entity> validTargets;

		GameAction attackAction = new PhysicalAttackAction(attacker.getReference());
		validTargets = context.getLogic().getValidTargets(mage.getId(), attackAction);
		Assert.assertEquals(validTargets.size(), 1);

		GameAction fireblast = mage.getHero().getHeroPower().play();
		validTargets = context.getLogic().getValidTargets(mage.getId(), fireblast);
		Assert.assertEquals(validTargets.size(), 4);

		defender.removeAttribute(Attribute.TAUNT);

		validTargets = context.getLogic().getValidTargets(mage.getId(), attackAction);
		Assert.assertEquals(validTargets.size(), 2);

		validTargets = context.getLogic().getValidTargets(mage.getId(), fireblast);
		Assert.assertEquals(validTargets.size(), 4);

		// taunt should be ignored when the minion is stealthed
		defender.setAttribute(Attribute.TAUNT);
		defender.setAttribute(Attribute.STEALTH);
		validTargets = context.getLogic().getValidTargets(mage.getId(), attackAction);
		Assert.assertEquals(validTargets.size(), 1);
		Assert.assertEquals(validTargets.get(0).getEntityType(), EntityType.HERO);

	}

}
