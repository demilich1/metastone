package net.pferdimanzug.hearthstone.analyzer.gui.trainingmode;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.FeatureVector;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.GameStateValueBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.ga.GeneticFeatureOptimizer;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroFactory;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.Statistic;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.DeckProxy;

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
		// for now add all decks to the training set
		DeckProxy deckProxy = (DeckProxy) getFacade().retrieveProxy(DeckProxy.NAME);
		config.getDecks().add(deckProxy.getDeckByName("Colma Tempo Warrior, top 50 legend"));
		if (config.getDecks().isEmpty()) {
			logger.error("Deck list is empty!!");
		}
		// config.getDecks().addAll(deckProxy.getDecks());

		gamesCompleted = 0;
		gamesWon = 0;

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				logger.info("Training started");

				GeneticFeatureOptimizer learner = new GeneticFeatureOptimizer();
				Deck player1Deck = config.getRandomDeck();
				Deck player2Deck = config.getRandomDeck();
				learner.init(player1Deck, player2Deck);

				// send initial status update
				TrainingProgressReport progress = new TrainingProgressReport(gamesCompleted, config.getNumberOfGames(), gamesWon);
				getFacade().sendNotification(GameNotification.TRAINING_PROGRESS_UPDATE, progress);
				for (int i = 0; i < config.getNumberOfGames(); i++) {
					FeatureVector fittest = learner.getBest();
					
					Player player1 = new Player("Learner", HeroFactory.createHero(player1Deck.getHeroClass()), player1Deck);
					player1.setBehaviour(new GameStateValueBehaviour(fittest));

					Player player2 = new Player("Opponent", HeroFactory.createHero(player2Deck.getHeroClass()), player2Deck);
					player2.setBehaviour(new GameStateValueBehaviour(FeatureVector.getDefault()));

					GameContext newGame = new GameContext(player1, player2, new GameLogic());
					newGame.play();

					onGameComplete(config, newGame);
					newGame.dispose();
					
					System.out.println(fittest);
					learner.train();
				}

				getFacade().sendNotification(GameNotification.TRAINING_PROGRESS_UPDATE,
						new TrainingProgressReport(gamesCompleted, config.getNumberOfGames(), gamesWon));
				logger.info("Training ended");
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
