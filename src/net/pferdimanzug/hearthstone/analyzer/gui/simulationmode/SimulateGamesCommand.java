package net.pferdimanzug.hearthstone.analyzer.gui.simulationmode;

import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.GameConfig;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.PlayerConfig;
import net.pferdimanzug.hearthstone.analyzer.utils.Tuple;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class SimulateGamesCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		final GameConfig gameConfig = (GameConfig) notification.getBody();
		final SimulationResult result = new SimulationResult();

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < gameConfig.getNumberOfGames(); i++) {
					playGame(gameConfig, result);
					Tuple<Integer> progress = new Tuple<Integer>(i + 1, gameConfig.getNumberOfGames());
					getFacade().sendNotification(GameNotification.SIMULATION_PROGRESS_UPDATE, progress);
				}
				// all games finished
				getFacade().sendNotification(GameNotification.SIMULATION_RESULT, result);
			}
		});
		t.setDaemon(true);
		t.start();
	}

	private void playGame(GameConfig gameConfig, SimulationResult result) {

		PlayerConfig playerConfig1 = gameConfig.getPlayerConfig1();
		PlayerConfig playerConfig2 = gameConfig.getPlayerConfig2();

		Player player1 = new Player("Player 1", playerConfig1.getHero().clone(), playerConfig1.getDeck());
		player1.setBehaviour(playerConfig1.getBehaviour());

		Player player2 = new Player("Player 2", playerConfig2.getHero().clone(), playerConfig2.getDeck());
		player2.setBehaviour(playerConfig2.getBehaviour());

		GameContext newGame = new GameContext(player1, player2, new GameLogic());

		ApplicationFacade.getInstance().sendNotification(GameNotification.PLAY_GAME, newGame);

		result.getPlayer1Stats().merge(player1.getStatistics());
		result.getPlayer2Stats().merge(player2.getStatistics());
	}

}
