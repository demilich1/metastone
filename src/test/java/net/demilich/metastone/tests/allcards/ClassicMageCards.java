package net.demilich.metastone.tests.allcards;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.tests.TestBase;

public class ClassicMageCards extends TestBase {
	
	@Test
	public void testBlizzard() {
		GameContext context = createContext(HeroClass.MAGE, HeroClass.WARLOCK);
		Player player = context.getPlayer1();
		Player opponent = context.getPlayer2();
		
		context.endTurn();
		Minion impGangBoss = playMinionCard(context, opponent, (MinionCard) CardCatalogue.getCardById("minion_imp_gang_boss"));
		context.endTurn();
		
		playCard(context, player, CardCatalogue.getCardById("spell_blizzard"));
		
		Assert.assertEquals(impGangBoss.getHp(), impGangBoss.getMaxHp() - 2);
		for (Minion minion : opponent.getMinions()) {
			Assert.assertEquals(minion.hasAttribute(Attribute.FROZEN), true);	
		}
	}

}
