package net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.GreedyOptimizeMove;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.heuristic.WeightedHeuristic;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.FeatureVector;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.GameStateValueBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroFactory;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.GameStatistics;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.Statistic;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class WinRateFitnessFunction extends FitnessFunction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2616303666130026770L;
	
	private static final int NUMBER_OF_GAMES = 20;
	
	private final Deck deck1;
	private final Deck deck2;

	public WinRateFitnessFunction(Deck deck1, Deck deck2) {
		this.deck1 = deck1;
		this.deck2 = deck2;
	}

	@Override
	protected double evaluate(IChromosome chromosome) {
		GameStatistics stats = new GameStatistics();
		
		ExecutorService executor = Executors.newWorkStealingPool();
		List<Future<Void>> futures = new ArrayList<Future<Void>>(); 
		for (int i = 0; i < NUMBER_OF_GAMES; i++) {
			PlayGameTask task = new PlayGameTask(stats, chromosome);
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
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return stats.getDouble(Statistic.WIN_RATE) * 100;
	}
	
	
	private class PlayGameTask implements Callable<Void> {

		private final GameStatistics stats;
		private final IChromosome chromosome;

		public PlayGameTask(GameStatistics stats, IChromosome chromosome) {
			this.stats = stats;
			this.chromosome = chromosome;
		}

		@Override
		public Void call() throws Exception {
			Hero hero1 = HeroFactory.createHero(deck1.getHeroClass());
			Player player1 = new Player("Player 1 (learning)", hero1, deck1);
			FeatureVector vector = GeneticFeatureOptimizer.toVector(chromosome);
			player1.setBehaviour(new GameStateValueBehaviour(vector));

			Hero hero2 = HeroFactory.createHero(deck2.getHeroClass());
			Player player2 = new Player("Player 2 (static)", hero2, deck2);
			player2.setBehaviour(new GreedyOptimizeMove(new WeightedHeuristic()));

			GameContext newGame = new GameContext(player1, player2, new GameLogic());
			newGame.play();
			synchronized (stats) {
				stats.merge(player1.getStatistics());
			}
			
			newGame.dispose();
			
			return null;
		}

	}

}
