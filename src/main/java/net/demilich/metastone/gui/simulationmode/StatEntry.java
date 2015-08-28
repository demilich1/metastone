package net.demilich.metastone.gui.simulationmode;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StatEntry {
	private final StringProperty statName = new SimpleStringProperty(this, "statName");
	private final StringProperty player1Value = new SimpleStringProperty(this, "player1Value");
	private final StringProperty player2Value = new SimpleStringProperty(this, "player2Value");

	public StatEntry() {
	}

	public StatEntry(String statName, String player1Value, String player2Value) {
		setStatName(statName);
		setPlayer1Value(player1Value);
		setPlayer2Value(player2Value);
	}

	public String getPlayer1Value() {
		return player1Value.get();
	}

	public String getPlayer2Value() {
		return player2Value.get();
	}

	public String getStatName() {
		return statName.get();
	}

	public StringProperty player1ValueProperty() {
		return player1Value;
	}

	public StringProperty player2ValueProperty() {
		return player2Value;
	}

	public void setPlayer1Value(String value) {
		player1Value.set(value);
	}

	public void setPlayer2Value(String value) {
		player2Value.set(value);
	}

	public void setStatName(String value) {
		statName.set(value);
	}

	public StringProperty statNameProperty() {
		return statName;
	}

}
