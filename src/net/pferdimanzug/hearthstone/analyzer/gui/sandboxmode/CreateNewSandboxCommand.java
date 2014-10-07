package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.DoNothingBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.decks.DeckFactory;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroFactory;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class CreateNewSandboxCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		SandboxProxy sandboxProxy = (SandboxProxy) getFacade().retrieveProxy(SandboxProxy.NAME);

		Hero hero1 = HeroFactory.createHero(HeroClass.MAGE);
		Player player1 = new Player("Player 1", hero1, DeckFactory.getRandomDeck(hero1.getHeroClass()));
		player1.setBehaviour(new DoNothingBehaviour());

		Hero hero2 = HeroFactory.createHero(HeroClass.WARRIOR);
		Player player2 = new Player("Player 2", hero2, DeckFactory.getRandomDeck(hero2.getHeroClass()));
		player2.setBehaviour(new DoNothingBehaviour());

		GameContext sandbox = new GameContext(player1, player2, new GameLogic());
		sandbox.init();
		sandboxProxy.setSandbox(sandbox);
		sendNotification(GameNotification.UPDATE_SANDBOX_STATE, sandbox);
	}

}
