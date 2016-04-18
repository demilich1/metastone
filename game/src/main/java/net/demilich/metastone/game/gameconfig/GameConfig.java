package net.demilich.metastone.game.gameconfig;

import net.demilich.metastone.game.decks.DeckFormat;

public class GameConfig {

	private int numberOfGames;
	private PlayerConfig playerConfig1;
	private PlayerConfig playerConfig2;
	private DeckFormat deckFormat;

	public DeckFormat getDeckFormat() {
		return deckFormat;
	}

	public int getNumberOfGames() {
		return numberOfGames;
	}

	public PlayerConfig getPlayerConfig1() {
		return playerConfig1;
	}

	public PlayerConfig getPlayerConfig2() {
		return playerConfig2;
	}

	public void setDeckFormat(DeckFormat deckFormat) {
		this.deckFormat = deckFormat;
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
