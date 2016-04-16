package net.demilich.metastone.game.statistics;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.weapons.Weapon;

public class GameStatistics implements Cloneable {

	private final Map<Statistic, Object> stats = new EnumMap<Statistic, Object>(Statistic.class);
	private final Map<String, Integer> cardsPlayed = new HashMap<String, Integer>();
	private final Map<String, Integer> minionsSummoned = new HashMap<String, Integer>();

	private void add(Statistic key, long value) {
		if (!stats.containsKey(key)) {
			stats.put(key, 0L);
		}
		long newValue = getLong(key) + value;
		stats.put(key, newValue);
	}

	public void armorGained(int armor) {
		add(Statistic.ARMOR_GAINED, armor);
	}

	public void cardDrawn() {
		add(Statistic.CARDS_DRAWN, 1);
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
		case CHOOSE_ONE:
			add(Statistic.SPELLS_CAST, 1);
			break;
		case WEAPON:
			add(Statistic.WEAPONS_PLAYED, 1);
		case HERO:
			break;
		}
		increaseCardCount(card);
	}

	public GameStatistics clone() {
		GameStatistics clone = new GameStatistics();
		clone.stats.putAll(stats);
		clone.getCardsPlayed().putAll(getCardsPlayed());
		clone.getMinionsSummoned().putAll(getMinionsSummoned());
		return clone;
	}

	public boolean contains(Statistic key) {
		return stats.containsKey(key);
	}

	public void damageDealt(int damage) {
		add(Statistic.DAMAGE_DEALT, damage);
	}

	public void equipWeapon(Weapon weapon) {
		add(Statistic.WEAPONS_EQUIPPED, 1);
	}

	public void fatigueDamage(int fatigueDamage) {
		add(Statistic.FATIGUE_DAMAGE, fatigueDamage);
	}

	public void gameLost() {
		add(Statistic.GAMES_LOST, 1);
		updateWinRate();
	}

	public void gameWon() {
		add(Statistic.GAMES_WON, 1);
		updateWinRate();
	}

	public Object get(Statistic key) {
		return stats.get(key);
	}

	public Map<String, Integer> getCardsPlayed() {
		return cardsPlayed;
	}
	
	public Map<String, Integer> getMinionsSummoned() {
		return minionsSummoned;
	}

	public double getDouble(Statistic key) {
		return stats.containsKey(key) ? (double) stats.get(key) : 0.0;
	}

	public long getLong(Statistic key) {
		return stats.containsKey(key) ? (long) stats.get(key) : 0L;
	}

	public void heal(int healing) {
		add(Statistic.HEALING_DONE, healing);
	}

	private void increaseCardCount(Card card) {
		if (card.getCardType().isCardType(CardType.HERO_POWER)) {
			return;
		}
		String cardId = card.getCardId();
		;
		if (!getCardsPlayed().containsKey(cardId)) {
			getCardsPlayed().put(cardId, 0);
		}
		getCardsPlayed().put(cardId, getCardsPlayed().get(cardId) + 1);
	}
	
	private void increaseMinionCount(Minion minion) {
		String cardId = minion.getSourceCard().getCardId();
		if (!getMinionsSummoned().containsKey(cardId)) {
			getMinionsSummoned().put(cardId, 0);
		}
		getMinionsSummoned().put(cardId, getMinionsSummoned().get(cardId) + 1);
	}

	public void manaSpent(int mana) {
		add(Statistic.MANA_SPENT, mana);
	}

	public void merge(GameStatistics otherStatistics) {
		for (Statistic stat : otherStatistics.stats.keySet()) {
			Object value = get(stat);
			if (value != null) {
				if (value instanceof Long) {
					add(stat, otherStatistics.getLong(stat));
				}
			} else {
				stats.put(stat, otherStatistics.get(stat));
			}
		}
		for (String cardId : otherStatistics.getCardsPlayed().keySet()) {
			if (!getCardsPlayed().containsKey(cardId)) {
				getCardsPlayed().put(cardId, 0);
			}
			getCardsPlayed().put(cardId, getCardsPlayed().get(cardId) + otherStatistics.getCardsPlayed().get(cardId));
		}
		updateWinRate();
	}
	
	public void minionSummoned(Minion minion) {
		add(Statistic.MINIONS_PLAYED, 1);

		increaseMinionCount(minion);
	}

	public void set(Statistic key, Object value) {
		stats.put(key, value);
	}

	public void startTurn() {
		add(Statistic.TURNS_TAKEN, 1);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[GameStatistics]\n");
		for (Statistic stat : stats.keySet()) {
			builder.append(stat);
			builder.append(": ");
			builder.append(stats.get(stat));
			builder.append("\n");
		}
		return builder.toString();
	}

	private void updateWinRate() {
		double winRate = getLong(Statistic.GAMES_WON) / (double) (getLong(Statistic.GAMES_WON) + getLong(Statistic.GAMES_LOST));
		set(Statistic.WIN_RATE, winRate);
	}

}
