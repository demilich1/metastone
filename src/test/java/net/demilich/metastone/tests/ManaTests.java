package net.demilich.metastone.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.entities.heroes.HeroClass;

public class ManaTests extends TestBase {
	
	@Test
	public void testDarnassusAspirant() {
		GameContext context = createContext(HeroClass.DRUID, HeroClass.WARRIOR);
		Player player = context.getPlayer1();
		int playerId = player.getId();

		context.getLogic().startTurn(playerId);
		player.setMana(4);
		player.setMaxMana(4);
		Assert.assertEquals(player.getMana(), 4);
		Assert.assertEquals(player.getMaxMana(), 4);
		playCard(context, player, CardCatalogue.getCardById("minion_darnassus_aspirant"));
		Assert.assertEquals(player.getMana(), 2);
		Assert.assertEquals(player.getMaxMana(), 5);
		playCard(context, player, CardCatalogue.getCardById("minion_doomsayer"));
		Assert.assertEquals(player.getMana(), 0);
		Assert.assertEquals(player.getMaxMana(), 5);
		context.getLogic().endTurn(playerId);
		
		// start turn - Doomsayer triggers and kills Darnassus Aspirant
		context.getLogic().startTurn(playerId);
		// player should loose a full mana crystal in this case
		Assert.assertEquals(player.getMana(), 5);
		Assert.assertEquals(player.getMaxMana(), 5);
	}

}
