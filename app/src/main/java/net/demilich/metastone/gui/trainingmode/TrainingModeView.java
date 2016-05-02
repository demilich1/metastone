package net.demilich.metastone.gui.trainingmode;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;

public class TrainingModeView extends BorderPane implements EventHandler<ActionEvent> {

	@FXML
	private Button backButton;

	@FXML
	private Label trainingLabel;

	@FXML
	private Label progressLabel;

	@FXML
	private LineChart<Number, Number> resultChart;

	private XYChart.Series<Number, Number> series;

	public TrainingModeView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TrainingModeView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		backButton.setOnAction(this);

		resultChart.setVisible(false);
	}

	@Override
	public void handle(ActionEvent actionEvent) {
		if (actionEvent.getSource() == backButton) {
			NotificationProxy.sendNotification(GameNotification.MAIN_MENU);
		}
	}

	public void setDeckName(String deckname) {
		trainingLabel.setText("Training: " + deckname);
	}

	public void showProgress(TrainingProgressReport progress) {
		progressLabel.setText(progress.getGamesCompleted() + " out of " + progress.getGamesTotal() + " games completed");
		int progressMark = Math.max(progress.getGamesTotal() / 100, 10);
		if (progress.getGamesCompleted() == 0 || progress.getGamesCompleted() % progressMark != 0) {
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
		backButton.setDisable(true);
		resultChart.setVisible(true);
		series = new XYChart.Series<>();
		series.setName("Win rate");
		resultChart.getData().add(series);
		series.getData().add(new XYChart.Data<Number, Number>(0, 0));
	}

}
