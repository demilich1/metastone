package net.pferdimanzug.hearthstone.analyzer.gui.simulationmode;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.GameStatistics;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.Statistic;

import org.apache.commons.lang3.time.DurationFormatUtils;

public class SimulationResultView extends BorderPane {
	
	@FXML
	private BorderPane infoArea;
	
	@FXML
	private TableView<StatEntry> resultTable;

	@FXML
	private Button doneButton;
	
	@FXML
	private Label durationLabel;
	
	private PlayerInfoView player1InfoView;
	private PlayerInfoView player2InfoView;

	public SimulationResultView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SimulationResultView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		doneButton.setOnAction(event -> ApplicationFacade.getInstance().sendNotification(GameNotification.MAIN_MENU));

		player1InfoView = new PlayerInfoView();
		infoArea.setLeft(player1InfoView);
		player2InfoView = new PlayerInfoView();
		infoArea.setRight(player2InfoView);
	}

	private String getStatString(Statistic stat, GameStatistics playerStatistics) {
		if (playerStatistics.contains(stat)) {
			return String.valueOf(playerStatistics.get(stat));
		}
		return "-";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void showSimulationResult(SimulationResult result) {
		player1InfoView.setInfo(result.getConfig().getPlayerConfig1());
		player2InfoView.setInfo(result.getConfig().getPlayerConfig2());
		durationLabel.setText("Simulation took " + DurationFormatUtils.formatDurationHMS(result.getDuration()));
		
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
