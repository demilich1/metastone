package net.demilich.metastone.gui.battleofdecks;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.demilich.metastone.game.statistics.GameStatistics;
import net.demilich.metastone.game.statistics.Statistic;

public class BattleDeckResult {

	private final StringProperty deckName = new SimpleStringProperty();
	private final ObjectProperty<GameStatistics> deckStatistics = new SimpleObjectProperty<>();
	private final DoubleProperty winRate = new SimpleDoubleProperty();

	public BattleDeckResult(String deckName, GameStatistics deckStatistics) {
		setDeckName(deckName);
		setDeckStatistics(deckStatistics);
		setWinRate(deckStatistics.getDouble(Statistic.WIN_RATE));
	}

	public final StringProperty deckNameProperty() {
		return this.deckName;
	}

	public final ObjectProperty<GameStatistics> deckStatisticsProperty() {
		return this.deckStatistics;
	}

	public final String getDeckName() {
		return this.deckNameProperty().get();
	}

	public final GameStatistics getDeckStatistics() {
		return this.deckStatisticsProperty().get();
	}

	public final double getWinRate() {
		return this.winRateProperty().get();
	}

	public final void setDeckName(final String deckName) {
		this.deckNameProperty().set(deckName);
	}

	public final void setDeckStatistics(final GameStatistics deckStatistics) {
		this.deckStatisticsProperty().set(deckStatistics);
	}

	public final void setWinRate(final double winRate) {
		this.winRateProperty().set(winRate);
	}

	public final DoubleProperty winRateProperty() {
		return this.winRate;
	}

}
