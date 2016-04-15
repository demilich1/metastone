package net.demilich.metastone.gui.trainingmode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.nittygrittymvc.Notification;
import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.behaviour.threat.FeatureVector;
import net.demilich.metastone.game.behaviour.threat.GameStateValueBehaviour;
import net.demilich.metastone.game.behaviour.threat.cuckoo.CuckooLearner;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.statistics.Statistic;
import net.demilich.metastone.game.gameconfig.PlayerConfig;

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

					PlayerConfig learnerConfig = new PlayerConfig(config.getDeckToTrain(),
							new GameStateValueBehaviour(fittest, "(fittest)"));
					learnerConfig.setName("Learner");
					Player player1 = new Player(learnerConfig);

					PlayerConfig opponentConfig = new PlayerConfig(config.getRandomDeck(), new GameStateValueBehaviour());
					opponentConfig.setName("Opponent");
					Player player2 = new Player(opponentConfig);
					
					DeckFormat deckFormat = new DeckFormat();
					for (CardSet set : CardSet.values()) {
						deckFormat.addSet(set);
					}

					GameContext newGame = new GameContext(player1, player2, new GameLogic(), deckFormat);
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
