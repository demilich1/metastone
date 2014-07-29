package net.pferdimanzug.hearthstone.analyzer.gui.simulationmode;

import net.pferdimanzug.hearthstone.analyzer.game.statistics.GameStatistics;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.Statistic;

public class SimulationResult {

	private final int numberOfGames;
	private final GameStatistics player1Stats = new GameStatistics();
	private final GameStatistics player2Stats = new GameStatistics();

	public SimulationResult(int numberOfGames) {
		this.numberOfGames = numberOfGames;
	}

	public void calculateMetaStatistics() {
		calculateMetaStatistics(player1Stats);
		calculateMetaStatistics(player2Stats);
	}

	private void calculateMetaStatistics(GameStatistics statistics) {
		float gamesPlayed = numberOfGames;
		float winRate = statistics.getInt(Statistic.GAMES_WON) / gamesPlayed;
		String winRateString = (winRate * 100) + "%";
		statistics.set(Statistic.WIN_RATE, winRateString);
		
		float avgTurns = statistics.getInt(Statistic.TURNS_TAKEN) / gamesPlayed;
		statistics.set(Statistic.AVG_TURNS_TAKEN, avgTurns);
	}
	
	public int getNumberOfGames() {
		return numberOfGames;
	}

	public GameStatistics getPlayer1Stats() {
		return player1Stats;
	}

	public GameStatistics getPlayer2Stats() {
		return player2Stats;
	}

}
