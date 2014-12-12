package net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.cuckoo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.FeatureVector;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.GameStateValueBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroFactory;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.GameStatistics;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.Statistic;

public class WinRateFitness implements IFitnessFunction {
	
	private static final int NUMBER_OF_GAMES = 50;
	
	private final Deck deckToTrain;
	private final List<Deck> decks;

	public WinRateFitness(Deck deckToTrain, List<Deck> decks) {
		this.deckToTrain = deckToTrain;
		this.decks = decks;
	}

	@Override
	public double evaluate(FeatureVector solution) {
		GameStatistics stats = new GameStatistics();
		
		int cores = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(cores);
		
		List<Future<Void>> futures = new ArrayList<Future<Void>>(); 
		for (int i = 0; i < NUMBER_OF_GAMES; i++) {
			PlayGameTask task = new PlayGameTask(stats, solution);
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
					e.printStackTrace();
				}
			}
			futures.removeIf(future -> future.isDone());
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return stats.getDouble(Statistic.WIN_RATE) * 100;
	}
	
	private Deck getRandomDeck() {
		return decks.get(ThreadLocalRandom.current().nextInt(decks.size()));
	}
	
	
	private class PlayGameTask implements Callable<Void> {

		private final GameStatistics stats;
		private final FeatureVector solution;

		public PlayGameTask(GameStatistics stats, FeatureVector solution) {
			this.stats = stats;
			this.solution = solution;
		}

		@Override
		public Void call() throws Exception {
			Deck deck1 = deckToTrain;
			Hero hero1 = HeroFactory.createHero(deck1.getHeroClass());
			Player player1 = new Player("Player 1 (learning)", hero1, deck1);
			player1.setBehaviour(new GameStateValueBehaviour(solution, "(current)"));

			Deck deck2 = getRandomDeck();
			Hero hero2 = HeroFactory.createHero(deck2.getHeroClass());
			Player player2 = new Player("Player 2 (static)", hero2, deck2);
			//player2.setBehaviour(new GameStateValueBehaviour(FeatureVector.getFittest(), "(former fittest)"));
			player2.setBehaviour(new GameStateValueBehaviour());

			GameContext newGame = new GameContext(player1, player2, new GameLogic());
			newGame.play();
			synchronized (stats) {
				stats.merge(player1.getStatistics());
			}
			
			return null;
		}

	}

}
