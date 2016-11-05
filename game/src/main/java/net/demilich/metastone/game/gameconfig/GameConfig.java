package net.demilich.metastone.game.gameconfig;

import net.demilich.metastone.game.decks.DeckFormat;

import java.io.Serializable;

public class GameConfig implements Cloneable, Serializable {

	private int numberOfGames;
	private PlayerConfig playerConfig1;
	private PlayerConfig playerConfig2;
	private DeckFormat deckFormat;
	private boolean isMultiplayer;
	private String host;
	private int port;

	public GameConfig() {
	}

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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isMultiplayer() {
		return isMultiplayer;
	}

	public void setMultiplayer(boolean multiplayer) {
		isMultiplayer = multiplayer;
	}

	public GameConfig clone() {
		GameConfig config = new GameConfig();
		config.setNumberOfGames(getNumberOfGames());
		config.setPlayerConfig1(getPlayerConfig1());
		config.setPlayerConfig2(getPlayerConfig2());
		config.setDeckFormat(getDeckFormat());
		config.setMultiplayer(isMultiplayer());
		config.setHost(getHost());
		config.setPort(getPort());
		return config;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[GameConfig]\n");
		builder.append("playerConfig1:\n");
		builder.append(getPlayerConfig1().toString());
		builder.append("\nplayerConfig2:\n");
		builder.append(getPlayerConfig2().toString());
		return builder.toString();
	}
}
