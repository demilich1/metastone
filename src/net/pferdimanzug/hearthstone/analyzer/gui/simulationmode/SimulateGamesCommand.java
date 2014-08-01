package net.pferdimanzug.hearthstone.analyzer.gui.simulationmode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.GameConfig;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.PlayerConfig;
import net.pferdimanzug.hearthstone.analyzer.utils.Tuple;
import de.pferdimanzug.nittygrittymvc.Notification;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class SimulateGamesCommand extends SimpleCommand<GameNotification> {

	private int gamesCompleted;

	@Override
	public void execute(INotification<GameNotification> notification) {
		final GameConfig gameConfig = (GameConfig) notification.getBody();
		final SimulationResult result = new SimulationResult(gameConfig.getNumberOfGames());
		long start = System.currentTimeMillis();

		gamesCompleted = 0;

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(8);
				List<PlayGameTask> tasks = new ArrayList<>(gameConfig.getNumberOfGames());

				for (int i = 0; i < gameConfig.getNumberOfGames(); i++) {
					PlayGameTask task = new PlayGameTask(gameConfig);
					tasks.add(task);
					executor.execute(task);
				}
				
				while(gamesCompleted < gameConfig.getNumberOfGames()) {
					Iterator<PlayGameTask> iterator = tasks.iterator();
					while (iterator.hasNext()) {
						PlayGameTask task = iterator.next();
						if (task.isDone()) {
							GameContext gameResult = task.getResult();
							result.getPlayer1Stats().merge(gameResult.getPlayer1().getStatistics());
							result.getPlayer2Stats().merge(gameResult.getPlayer2().getStatistics());
							onGameComplete(gameConfig);
							iterator.remove();
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}

				result.calculateMetaStatistics();
				getFacade().sendNotification(GameNotification.SIMULATION_RESULT, result);

			}
		});
		t.setDaemon(true);
		t.start();
	}

	private void onGameComplete(GameConfig gameConfig) {
		gamesCompleted++;
		Tuple<Integer, Integer> progress = new Tuple<>(gamesCompleted, gameConfig.getNumberOfGames());
		Notification<GameNotification> updateNotification = new Notification<>(GameNotification.SIMULATION_PROGRESS_UPDATE, progress);
		getFacade().notifyObservers(updateNotification);
	}

	private class PlayGameTask implements Runnable {

		private final GameConfig gameConfig;
		private GameContext result;

		public PlayGameTask(GameConfig gameConfig) {
			this.gameConfig = gameConfig;
		}
		
		public boolean isDone() {
			return getResult() != null;
		}

		@Override
		public void run() {
			PlayerConfig playerConfig1 = gameConfig.getPlayerConfig1();
			PlayerConfig playerConfig2 = gameConfig.getPlayerConfig2();

			Player player1 = new Player("Player 1", playerConfig1.getHero().clone(), playerConfig1.getDeck());
			player1.setBehaviour(playerConfig1.getBehaviour().clone());

			Player player2 = new Player("Player 2", playerConfig2.getHero().clone(), playerConfig2.getDeck());
			player2.setBehaviour(playerConfig2.getBehaviour().clone());

			GameContext newGame = new GameContext(player1, player2, new GameLogic());
			newGame.play();
			
			result = newGame;
		}

		public GameContext getResult() {
			return result;
		}

	}

}
