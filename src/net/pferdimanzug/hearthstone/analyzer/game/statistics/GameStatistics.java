package net.pferdimanzug.hearthstone.analyzer.game.statistics;

import java.util.HashMap;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

public class GameStatistics implements Cloneable {

	private final HashMap<Statistic, Object> stats = new HashMap<Statistic, Object>();

	private void add(Statistic key, int value) {
		if (!stats.containsKey(key)) {
			stats.put(key, 0);
		}
		int oldValue = getInt(key);
		stats.put(key, oldValue + value);
	}

	public void cardPlayed(Card card) {
		add(Statistic.CARDS_PLAYED, 1);

		switch (card.getCardType()) {
		case HERO_POWER:
			add(Statistic.HERO_POWER_USED, 1);
			break;
		case MINION:
			add(Statistic.MINIONS_PLAYED, 1);
			break;
		case SPELL:
			add(Statistic.SPELLS_CAST, 1);
			break;
		case WEAPON:
			add(Statistic.WEAPONS_EQUIPPED, 1);
			break;

		}
	}

	public GameStatistics clone() {
		GameStatistics clone = new GameStatistics();
		clone.stats.putAll(stats);
		return clone;
	}

	public boolean contains(Statistic key) {
		return stats.containsKey(key);
	}

	public void damageDealt(int damage) {
		add(Statistic.DAMAGE_DEALT, damage);
	}

	public void gameLost() {
		add(Statistic.GAMES_LOST, 1);
	}

	public void gameWon() {
		add(Statistic.GAMES_WON, 1);
	}

	public Object get(Statistic key) {
		return stats.get(key);
	}

	public int getInt(Statistic key) {
		return stats.containsKey(key) ? (int) stats.get(key) : 0;
	}

	public void heal(int healing) {
		add(Statistic.HEALING_DONE, healing);
	}
	
	public void manaSpent(int mana) {
		add(Statistic.MANA_SPENT, mana);
	}

	public void merge(GameStatistics otherStatistics) {
		for (Statistic stat : otherStatistics.stats.keySet()) {
			Object value = get(stat);
			if (value != null) {
				if (value instanceof Integer) {
					add(stat, (int) otherStatistics.stats.get(stat));
				}
			} else {
				stats.put(stat, otherStatistics.get(stat));
			}
		}
	}

	public void set(Statistic key, Object value) {
		stats.put(key, value);
	}

	public void startTurn() {
		add(Statistic.TURNS_TAKEN, 1);
	}

}
