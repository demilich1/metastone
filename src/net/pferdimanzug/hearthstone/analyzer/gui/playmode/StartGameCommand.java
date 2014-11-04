package net.pferdimanzug.hearthstone.analyzer.gui.playmode;

import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.GameConfig;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.PlayerConfig;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class StartGameCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		GameConfig gameConfig = (GameConfig) notification.getBody();

		PlayerConfig playerConfig1 = gameConfig.getPlayerConfig1();
		PlayerConfig playerConfig2 = gameConfig.getPlayerConfig2();

		Player player1 = new Player("Player 1", playerConfig1.getHero(), playerConfig1.getDeck());
		player1.setHideCards(playerConfig1.hideCards());
		player1.setBehaviour(playerConfig1.getBehaviour());

		Player player2 = new Player("Player 2", playerConfig2.getHero(), playerConfig2.getDeck());
		player2.setHideCards(playerConfig2.hideCards());
		player2.setBehaviour(playerConfig2.getBehaviour());

		GameContext newGame = new GameContextVisualizable(player1, player2, new GameLogic());
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				ApplicationFacade.getInstance().sendNotification(GameNotification.PLAY_GAME, newGame);
			}
		});
		t.setDaemon(true);
		t.start();
	}

}
