package net.demilich.metastone.gui.trainingmode;

public class TrainingProgressReport {

	private final int gamesCompleted;
	private final int gamesTotal;
	private final int gamesWon;

	public TrainingProgressReport(int gamesCompleted, int gamesTotal, int gamesWon) {
		this.gamesCompleted = gamesCompleted;
		this.gamesTotal = gamesTotal;
		this.gamesWon = gamesWon;
	}

	public int getGamesCompleted() {
		return gamesCompleted;
	}

	public int getGamesTotal() {
		return gamesTotal;
	}

	public int getGamesWon() {
		return gamesWon;
	}

}
