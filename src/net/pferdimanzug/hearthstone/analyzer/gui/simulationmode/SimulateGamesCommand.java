package net.pferdimanzug.hearthstone.analyzer.gui.simulationmode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroFactory;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.GameConfig;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.PlayerConfig;
import net.pferdimanzug.hearthstone.analyzer.utils.Tuple;

import org.apache.commons.lang3.exception.ExceptionUtils;
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
				int cores = Runtime.getRuntime().availableProcessors();
				logger.info("Starting simulation on " + cores + " cores");
				ExecutorService executor = Executors.newFixedThreadPool(cores);
				//ExecutorService executor = Executors.newSingleThreadExecutor();

				List<Future<Void>> futures = new ArrayList<Future<Void>>(); 
				// send initial status update
				Tuple<Integer, Integer> progress = new Tuple<>(0, gameConfig.getNumberOfGames());
				getFacade().sendNotification(GameNotification.SIMULATION_PROGRESS_UPDATE, progress);
				
				// queue up all games as tasks
				lastUpdate = System.currentTimeMillis();
				for (int i = 0; i < gameConfig.getNumberOfGames(); i++) {
					PlayGameTask task = new PlayGameTask(gameConfig);
					Future<Void> future = executor.submit(task);
					futures.add(future);
				}

				executor.shutdown();
				boolean completed = false;
				while (!completed) {
					completed = true;
					for (Future<Void> future : futures) {
						if (!future.isDone()) {
							completed = false;
							continue;
						}
						try {
							future.get();
						} catch (InterruptedException | ExecutionException e) {
							logger.error(ExceptionUtils.getStackTrace(e));
							e.printStackTrace();
							System.exit(-1);
						}
					}
					futures.removeIf(future -> future.isDone());
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
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

	private class PlayGameTask implements Callable<Void> {

		private final GameConfig gameConfig;

		public PlayGameTask(GameConfig gameConfig) {
			this.gameConfig = gameConfig;
		}

		@Override
		public Void call() throws Exception {
			PlayerConfig playerConfig1 = gameConfig.getPlayerConfig1();
			PlayerConfig playerConfig2 = gameConfig.getPlayerConfig2();

			Hero hero1 = HeroFactory.createHero(playerConfig1.getHero().getHeroClass());
			Player player1 = new Player("Player 1", hero1, playerConfig1.getDeck());
			player1.setBehaviour(playerConfig1.getBehaviour().clone());

			Hero hero2 = HeroFactory.createHero(playerConfig2.getHero().getHeroClass());
			Player player2 = new Player("Player 2", hero2, playerConfig2.getDeck());
			player2.setBehaviour(playerConfig2.getBehaviour().clone());

			GameContext newGame = new GameContext(player1, player2, new GameLogic());
			newGame.play();
			
			onGameComplete(gameConfig, newGame);
			newGame.dispose();
			
			return null;
		}

	}

}
