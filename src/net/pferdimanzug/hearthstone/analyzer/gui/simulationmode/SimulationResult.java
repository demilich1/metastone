package net.pferdimanzug.hearthstone.analyzer.gui.simulationmode;

import net.pferdimanzug.hearthstone.analyzer.game.statistics.GameStatistics;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.Statistic;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.GameConfig;

public class SimulationResult {

	private final GameStatistics player1Stats = new GameStatistics();
	private final GameStatistics player2Stats = new GameStatistics();
	private final long startTimestamp;
	private long duration;
	private final GameConfig config;

	public SimulationResult(GameConfig config) {
		this.config = config;
		this.startTimestamp = System.currentTimeMillis();
	}

	public void calculateMetaStatistics() {
		calculateMetaStatistics(player1Stats);
		calculateMetaStatistics(player2Stats);
	}

	private void calculateMetaStatistics(GameStatistics statistics) {
		float gamesPlayed = getNumberOfGames();
		float winRate = statistics.getInt(Statistic.GAMES_WON) / gamesPlayed;
		String winRateString = (winRate * 100) + "%";
		statistics.set(Statistic.WIN_RATE, winRateString);
		
		float avgTurns = statistics.getInt(Statistic.TURNS_TAKEN) / gamesPlayed;
		statistics.set(Statistic.AVG_TURNS_TAKEN, avgTurns);
		
		long endTimestamp = System.currentTimeMillis();
		duration = endTimestamp - startTimestamp;
	}
	
	public int getNumberOfGames() {
		return getConfig().getNumberOfGames();
	}

	public GameStatistics getPlayer1Stats() {
		return player1Stats;
	}

	public GameStatistics getPlayer2Stats() {
		return player2Stats;
	}
	
	public long getDuration() {
		return this.duration;
	}

	public GameConfig getConfig() {
		return config;
	}
	

}
