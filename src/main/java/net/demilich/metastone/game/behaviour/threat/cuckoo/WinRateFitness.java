package net.demilich.metastone.game.behaviour.threat.cuckoo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.behaviour.threat.FeatureVector;
import net.demilich.metastone.game.behaviour.threat.GameStateValueBehaviour;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.statistics.GameStatistics;
import net.demilich.metastone.game.statistics.Statistic;
import net.demilich.metastone.game.gameconfig.PlayerConfig;

public class WinRateFitness implements IFitnessFunction {

	private class PlayGameTask implements Callable<Void> {

		private final GameStatistics stats;
		private final FeatureVector solution;

		public PlayGameTask(GameStatistics stats, FeatureVector solution) {
			this.stats = stats;
			this.solution = solution;
		}

		@Override
		public Void call() throws Exception {
			PlayerConfig player1Config = new PlayerConfig(deckToTrain, new GameStateValueBehaviour(solution, "(current)"));
			player1Config.setName("Player 1 (learning)");
			Player player1 = new Player(player1Config);

			PlayerConfig player2Config = new PlayerConfig(getRandomDeck(), new GameStateValueBehaviour());
			player2Config.setName("Player 2 (static)");
			Player player2 = new Player(player2Config);

			DeckFormat deckFormat = new DeckFormat();
			for (CardSet set : CardSet.values()) {
				deckFormat.addSet(set);
			}

			GameContext newGame = new GameContext(player1, player2, new GameLogic(), deckFormat);
			newGame.play();
			synchronized (stats) {
				stats.merge(player1.getStatistics());
			}

			return null;
		}

	}

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
		double earlyWinRate = launchGames(solution, stats, 10);
		if (earlyWinRate < 40) {
			return earlyWinRate;
		}
		return launchGames(solution, stats, NUMBER_OF_GAMES - 10);

	}

	private Deck getRandomDeck() {
		return decks.get(ThreadLocalRandom.current().nextInt(decks.size()));
	}

	private double launchGames(FeatureVector solution, GameStatistics stats, int numberOfGames) {
		int cores = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(cores);

		List<Future<Void>> futures = new ArrayList<Future<Void>>();
		for (int i = 0; i < numberOfGames; i++) {
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

}
