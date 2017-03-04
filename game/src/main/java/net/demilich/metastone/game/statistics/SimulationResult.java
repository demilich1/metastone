package net.demilich.metastone.game.statistics;

import net.demilich.metastone.game.statistics.GameStatistics;
import net.demilich.metastone.game.statistics.Statistic;
import net.demilich.metastone.game.gameconfig.GameConfig;

import java.io.Serializable;

public class SimulationResult implements Cloneable, Serializable {
	private final GameStatistics player1Stats = new GameStatistics();
	private final GameStatistics player2Stats = new GameStatistics();
	private final long startTimestamp;
	private long duration;
	private final GameConfig config;

	public SimulationResult(GameConfig config) {
		this.config = config;
		this.startTimestamp = System.currentTimeMillis();
	}

	public SimulationResult merge(SimulationResult other) {
		getPlayer1Stats().merge(other.getPlayer1Stats());
		getPlayer2Stats().merge(other.getPlayer2Stats());
		duration += other.getDuration();
		config.setNumberOfGames(other.getConfig().getNumberOfGames() + getConfig().getNumberOfGames());
		return this;
	}

	public void calculateMetaStatistics() {
		calculateMetaStatistics(player1Stats);
		calculateMetaStatistics(player2Stats);
	}

	private void calculateMetaStatistics(GameStatistics statistics) {
		double gamesPlayed = getNumberOfGames();
		double winRate = statistics.getLong(Statistic.GAMES_WON) / gamesPlayed * 100;
		String winRateString = String.format("%.2f", winRate) + "%";
		statistics.set(Statistic.WIN_RATE, winRateString);

		long endTimestamp = System.currentTimeMillis();
		duration = endTimestamp - startTimestamp;
	}

	public GameConfig getConfig() {
		return config;
	}

	public long getDuration() {
		return this.duration;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[SimulationResult]\n");
		builder.append("config:\n");
		builder.append(config.toString());
		builder.append("\nplayer1Stats:\n");
		builder.append(getPlayer1Stats().toString());
		builder.append("\nplayer2Stats:\n");
		builder.append(getPlayer2Stats().toString());
		return builder.toString();
	}
}
