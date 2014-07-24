package net.pferdimanzug.hearthstone.analyzer.gui.gameconfig;

public class GameConfig {

	private int numberOfGames;
	private PlayerConfig playerConfig1;
	private PlayerConfig playerConfig2;

	public int getNumberOfGames() {
		return numberOfGames;
	}

	public PlayerConfig getPlayerConfig1() {
		return playerConfig1;
	}

	public PlayerConfig getPlayerConfig2() {
		return playerConfig2;
	}

	public void setNumberOfGames(int numberOfGames) {
		this.numberOfGames = numberOfGames;
	}

	public void setPlayerConfig1(PlayerConfig playerConfig1) {
		this.playerConfig1 = playerConfig1;
	}

	public void setPlayerConfig2(PlayerConfig playerConfig2) {
		this.playerConfig2 = playerConfig2;
	}

}
