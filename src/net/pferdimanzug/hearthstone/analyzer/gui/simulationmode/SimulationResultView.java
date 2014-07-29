package net.pferdimanzug.hearthstone.analyzer.gui.simulationmode;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.GameStatistics;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.Statistic;

public class SimulationResultView extends BorderPane {

	@FXML
	private TableView<StatEntry> resultTable;

	@FXML
	private Button doneButton;

	public SimulationResultView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SimulationResultView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

	}

	private String getStatString(Statistic stat, GameStatistics playerStatistics) {
		if (playerStatistics.contains(stat)) {
			return String.valueOf(playerStatistics.get(stat));
		}
		return "-";
	}

	public void showSimulationResult(SimulationResult result) {
		ObservableList<StatEntry> statEntries = FXCollections.observableArrayList();
		for (Statistic stat : Statistic.values()) {
			StatEntry statEntry = new StatEntry();
			statEntry.setStatName(stat.toString());
			statEntry.setPlayer1Value(getStatString(stat, result.getPlayer1Stats()));
			statEntry.setPlayer2Value(getStatString(stat, result.getPlayer2Stats()));
			statEntries.add(statEntry);

		}

		resultTable.setItems(statEntries);

		resultTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("statName"));
		resultTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("player1Value"));
		resultTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("player2Value"));
	}

}
