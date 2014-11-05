package net.pferdimanzug.hearthstone.analyzer.gui.trainingmode;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.learning.LearningBehaviour;

public class TrainingModeView extends BorderPane implements EventHandler<ActionEvent> {

	@FXML
	protected Button startButton;

	@FXML
	protected Button backButton;

	@FXML
	protected ComboBox<Integer> numberOfGamesBox;

	@FXML
	private LineChart<Number, Number> resultChart;

	private XYChart.Series<Number, Number> series;

	public TrainingModeView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TrainingModeView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		startButton.setOnAction(this);
		backButton.setOnAction(this);
		setupNumberOfGamesBox();

		resultChart.setVisible(false);
	}

	@Override
	public void handle(ActionEvent actionEvent) {
		if (actionEvent.getSource() == startButton) {
			TrainingConfig trainingConfig = new TrainingConfig(new LearningBehaviour(true));
			trainingConfig.setNumberOfGames(numberOfGamesBox.getSelectionModel().getSelectedItem());
			ApplicationFacade.getInstance().sendNotification(GameNotification.COMMIT_TRAININGMODE_CONFIG, trainingConfig);
		} else if (actionEvent.getSource() == backButton) {
			ApplicationFacade.getInstance().sendNotification(GameNotification.MAIN_MENU);
		}
	}

	private void setupNumberOfGamesBox() {
		ObservableList<Integer> numberOfGamesEntries = FXCollections.observableArrayList();
		numberOfGamesEntries.add(1);
		numberOfGamesEntries.add(10);
		numberOfGamesEntries.add(100);
		numberOfGamesEntries.add(1000);
		numberOfGamesEntries.add(10000);
		numberOfGamesEntries.add(100000);
		numberOfGamesBox.setItems(numberOfGamesEntries);
		numberOfGamesBox.getSelectionModel().select(2);
	}

	public void showProgress(TrainingProgressReport progress) {
		int progressMark = Math.max(progress.getGamesTotal() / 25, 10);
		if (progress.getGamesCompleted() % progressMark != 0 || progress.getGamesCompleted() == 0) {
			return;
		}
		double winRate = progress.getGamesWon() / (double) progress.getGamesCompleted();
		series.getData().add(new XYChart.Data<Number, Number>(progress.getGamesCompleted(), winRate));
		if (progress.getGamesCompleted() == progress.getGamesTotal()) {
			backButton.setDisable(false);
		}
	}

	public void startTraining() {
		resultChart.getData().clear();
		startButton.setDisable(true);
		backButton.setDisable(true);
		numberOfGamesBox.setDisable(true);
		resultChart.setVisible(true);
		series = new XYChart.Series<>();
		series.setName("Win rate");
		resultChart.getData().add(series);
		series.getData().add(new XYChart.Data<Number, Number>(0, 0));
	}

}
