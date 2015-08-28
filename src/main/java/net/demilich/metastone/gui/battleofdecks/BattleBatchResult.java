package net.demilich.metastone.gui.battleofdecks;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.statistics.GameStatistics;
import net.demilich.metastone.game.statistics.Statistic;

public class BattleBatchResult {

	private final int numberOfGames;
	private final GameStatistics player1Results = new GameStatistics();
	private final GameStatistics player2Results = new GameStatistics();
	private int gamesCompleted;
	private final Deck deck1;
	private final Deck deck2;
	private boolean completed;

	public BattleBatchResult(Deck deck1, Deck deck2, int numberOfGames) {
		this.deck1 = deck1;
		this.deck2 = deck2;
		this.numberOfGames = numberOfGames;
	}

	public Deck getDeck1() {
		return deck1;
	}

	public double getDeck1Winrate() {
		return getPlayer1Results().getDouble(Statistic.WIN_RATE);
	}

	public Deck getDeck2() {
		return deck2;
	}

	public double getDeck2Winrate() {
		return getPlayer2Results().getDouble(Statistic.WIN_RATE);
	}

	public int getNumberOfGames() {
		return numberOfGames;
	}

	public GameStatistics getPlayer1Results() {
		return player1Results;
	}

	public GameStatistics getPlayer2Results() {
		return player2Results;
	}

	public double getProgress() {
		return gamesCompleted / (double) numberOfGames;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void onGameEnded(GameContext result) {
		getPlayer1Results().merge(result.getPlayer1().getStatistics());
		getPlayer2Results().merge(result.getPlayer2().getStatistics());

		if (++gamesCompleted == numberOfGames) {
			setCompleted(true);
		}
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
