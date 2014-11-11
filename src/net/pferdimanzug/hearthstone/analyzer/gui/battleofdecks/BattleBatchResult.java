package net.pferdimanzug.hearthstone.analyzer.gui.battleofdecks;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.GameStatistics;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.Statistic;

public class BattleBatchResult {

	private final int numberOfGames;
	private final GameStatistics player1Results = new GameStatistics();
	private final GameStatistics player2Results = new GameStatistics();
	private int gamesCompleted;
	private final Deck deck1;
	private final Deck deck2;

	public BattleBatchResult(Deck deck1, Deck deck2, int numberOfGames) {
		this.deck1 = deck1;
		this.deck2 = deck2;
		this.numberOfGames = numberOfGames;
	}

	public void onGameEnded(GameContext result) {
		player1Results.merge(result.getPlayer1().getStatistics());

		player2Results.merge(result.getPlayer2().getStatistics());
		
		if (++gamesCompleted == numberOfGames) {
			double winrateDeck1 = player1Results.getLong(Statistic.GAMES_WON) / (double)numberOfGames;
			player1Results.set(Statistic.WIN_RATE, winrateDeck1);
			double winrateDeck2 = player2Results.getLong(Statistic.GAMES_WON) / (double)numberOfGames;
			player2Results.set(Statistic.WIN_RATE, winrateDeck2);
		}
	}

	public int getNumberOfGames() {
		return numberOfGames;
	}

	public double getDeck1Winrate() {
		return player1Results.getDouble(Statistic.WIN_RATE);
	}
	
	public double getDeck2Winrate() {
		return player2Results.getDouble(Statistic.WIN_RATE);
	}

	public Deck getDeck1() {
		return deck1;
	}

	public Deck getDeck2() {
		return deck2;
	}
}
