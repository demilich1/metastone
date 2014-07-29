package net.pferdimanzug.hearthstone.analyzer.game.statistics;

import java.util.HashMap;

public class GameStatistics implements Cloneable {

	private final HashMap<Statistic, Integer> stats = new HashMap<Statistic, Integer>();

	public GameStatistics clone() {
		GameStatistics clone = new GameStatistics();
		clone.stats.putAll(stats);
		return clone;
	}

	public void add(Statistic key, int value) {
		if (!stats.containsKey(key)) {
			stats.put(key, 0);
		}
		int oldValue = stats.get(key);
		stats.put(key, oldValue + value);
	}

	public void merge(GameStatistics otherStatistics) {
		for (Statistic stat : otherStatistics.stats.keySet()) {
			add(stat, otherStatistics.stats.get(stat));
		}
	}

	public int get(Statistic key) {
		return stats.get(key);
	}

	public boolean contains(Statistic key) {
		return stats.containsKey(key);
	}

}
