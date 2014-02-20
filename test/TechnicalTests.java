import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PhysicalAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.AmaniBerserker;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock.Corruption;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Garrosh;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Guldan;

import org.testng.Assert;
import org.testng.annotations.Test;


public class TechnicalTests extends TestBase {
	
	@Test
	public void testDoubleCorruption() {
		GameContext context = createContext(new Guldan(), new Garrosh());
		Player warlock = context.getPlayer1();
		warlock.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		TestMinionCard victimCard = new TestMinionCard(1, 10);
		context.getLogic().receiveCard(warrior.getId(), victimCard);
		context.getLogic().performGameAction(warrior.getId(), victimCard.play());
		
		Corruption corruption1 = new Corruption();
		Corruption corruption2 = new Corruption();
		context.getLogic().receiveCard(warlock.getId(), corruption1);
		context.getLogic().receiveCard(warlock.getId(), corruption2);
		
		Entity victim = getSingleMinion(warrior.getMinions());
		GameAction playCorruption1 = corruption1.play();
		playCorruption1.setTarget(victim);
		GameAction playCorruption2 = corruption2.play();
		playCorruption2.setTarget(victim);
		
		context.getLogic().performGameAction(warlock.getId(), playCorruption1);
		context.getLogic().performGameAction(warlock.getId(), playCorruption2);
		
		context.getLogic().endTurn(GameContext.PLAYER_1);
		context.getLogic().startTurn(GameContext.PLAYER_2);
		context.getLogic().endTurn(GameContext.PLAYER_2);
		context.getLogic().startTurn(GameContext.PLAYER_1);
		
	}
}
