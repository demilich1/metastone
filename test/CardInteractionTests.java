import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.BloodsailRaider;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.MurlocRaider;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.ArcaniteReaper;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.WarsongCommander;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Garrosh;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Jaina;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

import org.testng.Assert;
import org.testng.annotations.Test;


public class CardInteractionTests extends TestBase {
	
	@Test
	public void testWarriorCards() {
		GameContext context = createContext(new Garrosh(), new Jaina());
		Player warrior = context.getPlayer1();
		warrior.setMana(10);
		
		playCard(context, warrior, new ArcaniteReaper());
		playCard(context, warrior, new WarsongCommander());
		playCard(context, warrior, new MurlocRaider());
		
		Minion bloodsailRaider = playMinionCard(context, warrior, new BloodsailRaider());
		Assert.assertTrue(bloodsailRaider.hasTag(GameTag.CHARGE));
		Assert.assertEquals(bloodsailRaider.getAttack(), 7);
	}

}
