package net.pferdimanzug.hearthstone.analyzer.gui.trainingmode;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.FeatureVector;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.GameStateValueBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.cuckoo.CuckooLearner;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.Statistic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pferdimanzug.nittygrittymvc.Notification;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class PerformTrainingCommand extends SimpleCommand<GameNotification> {

	private static Logger logger = LoggerFactory.getLogger(PerformTrainingCommand.class);

	private int gamesCompleted;
	private int gamesWon;

	@Override
	public void execute(INotification<GameNotification> notification) {
		final TrainingConfig config = (TrainingConfig) notification.getBody();
		if (config.getDecks().isEmpty()) {
			logger.error("Deck list is empty!!");
		}

		gamesCompleted = 0;
		gamesWon = 0;

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				logger.info("Training started");

				CuckooLearner learner = new CuckooLearner(config.getDeckToTrain(), config.getDecks());

				// send initial status update
				TrainingProgressReport progress = new TrainingProgressReport(gamesCompleted, config.getNumberOfGames(), gamesWon);
				getFacade().sendNotification(GameNotification.TRAINING_PROGRESS_UPDATE, progress);
				for (int i = 0; i < config.getNumberOfGames(); i++) {
					FeatureVector fittest = learner.getFittest();

					Deck player1Deck = config.getDeckToTrain();
					Deck player2Deck = config.getRandomDeck();
					Player player1 = new Player("Learner", player1Deck);
					player1.setBehaviour(new GameStateValueBehaviour(fittest, "(fittest)"));

					Player player2 = new Player("Opponent", player2Deck);
					player2.setBehaviour(new GameStateValueBehaviour());

					GameContext newGame = new GameContext(player1, player2, new GameLogic());
					newGame.play();

					onGameComplete(config, newGame);
					newGame.dispose();

					if (i % 10 == 0) {
						learner.evolve();
					}
				}

				getFacade().sendNotification(GameNotification.TRAINING_PROGRESS_UPDATE,
						new TrainingProgressReport(gamesCompleted, config.getNumberOfGames(), gamesWon));

				logger.info("Training ended");
				FeatureVector fittest = learner.getFittest();
				logger.info("**************Final weights: {}", fittest);

				// save training data
				getFacade().sendNotification(GameNotification.SAVE_TRAINING_DATA,
						new TrainingData(config.getDeckToTrain().getName(), fittest));
			}
		});
		t.setDaemon(true);
		t.start();
	}

	private void onGameComplete(TrainingConfig config, GameContext completedGame) {
		gamesCompleted++;

		gamesWon += completedGame.getPlayer1().getStatistics().getLong(Statistic.GAMES_WON);
		TrainingProgressReport progress = new TrainingProgressReport(gamesCompleted, config.getNumberOfGames(), gamesWon);
		Notification<GameNotification> updateNotification = new Notification<>(GameNotification.TRAINING_PROGRESS_UPDATE, progress);
		getFacade().notifyObservers(updateNotification);
	}

}
