package net.pferdimanzug.hearthstone.analyzer.gui.simulationmode;

import net.pferdimanzug.hearthstone.analyzer.game.statistics.GameStatistics;

public class SimulationResult {

	private final GameStatistics player1Stats;
	private final GameStatistics player2Stats;

	public SimulationResult(GameStatistics player1Stats, GameStatistics player2Stats) {
		this.player1Stats = player1Stats;
		this.player2Stats = player2Stats;
	}

	public SimulationResult() {
		this(new GameStatistics(), new GameStatistics());
	}

	public GameStatistics getPlayer1Stats() {
		return player1Stats;
	}

	public GameStatistics getPlayer2Stats() {
		return player2Stats;
	}

}
