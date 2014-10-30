package net.pferdimanzug.hearthstone.analyzer.gui.simulationmode;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.GameConfig;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.PlayerConfig;
import net.pferdimanzug.hearthstone.analyzer.utils.Tuple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pferdimanzug.nittygrittymvc.Notification;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class SimulateGamesCommand extends SimpleCommand<GameNotification> {

	private static Logger logger = LoggerFactory.getLogger(SimulateGamesCommand.class);

	private int gamesCompleted;
	private long lastUpdate;
	private SimulationResult result;

	@Override
	public void execute(INotification<GameNotification> notification) {
		final GameConfig gameConfig = (GameConfig) notification.getBody();
		result = new SimulationResult(gameConfig);

		gamesCompleted = 0;

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				logger.info("Simulation started");
				ExecutorService executor = Executors.newWorkStealingPool();
				//ExecutorService executor = Executors.newSingleThreadExecutor();

				// send initial status update
				Tuple<Integer, Integer> progress = new Tuple<>(0, gameConfig.getNumberOfGames());
				getFacade().sendNotification(GameNotification.SIMULATION_PROGRESS_UPDATE, progress);
				lastUpdate = System.currentTimeMillis();
				for (int i = 0; i < gameConfig.getNumberOfGames(); i++) {
					PlayGameTask task = new PlayGameTask(gameConfig);
					executor.submit(task);
				}

				executor.shutdown();
				try {
					executor.awaitTermination(10000, TimeUnit.HOURS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				result.calculateMetaStatistics();
				getFacade().sendNotification(GameNotification.SIMULATION_RESULT, result);
				logger.info("Simulation finished");

			}
		});
		t.setDaemon(true);
		t.start();
	}

	private void onGameComplete(GameConfig gameConfig, GameContext context) {
		long timeStamp = System.currentTimeMillis();
		gamesCompleted++;
		if (timeStamp - lastUpdate > 100) {
			lastUpdate = timeStamp;
			Tuple<Integer, Integer> progress = new Tuple<>(gamesCompleted, gameConfig.getNumberOfGames());
			Notification<GameNotification> updateNotification = new Notification<>(GameNotification.SIMULATION_PROGRESS_UPDATE, progress);
			getFacade().notifyObservers(updateNotification);
		}
		synchronized (result) {
			result.getPlayer1Stats().merge(context.getPlayer1().getStatistics());
			result.getPlayer2Stats().merge(context.getPlayer2().getStatistics());
		}
	}

	private class PlayGameTask implements Callable<GameContext> {

		private final GameConfig gameConfig;

		public PlayGameTask(GameConfig gameConfig) {
			this.gameConfig = gameConfig;
		}

		@Override
		public GameContext call() throws Exception {
			PlayerConfig playerConfig1 = gameConfig.getPlayerConfig1();
			PlayerConfig playerConfig2 = gameConfig.getPlayerConfig2();

			Player player1 = new Player("Player 1", playerConfig1.getHero().clone(), playerConfig1.getDeck());
			player1.setBehaviour(playerConfig1.getBehaviour().clone());

			Player player2 = new Player("Player 2", playerConfig2.getHero().clone(), playerConfig2.getDeck());
			player2.setBehaviour(playerConfig2.getBehaviour().clone());

			GameContext newGame = new GameContext(player1, player2, new GameLogic());
			newGame.play();

			onGameComplete(gameConfig, newGame);
			newGame.dispose();
			return newGame;
		}

	}

}
