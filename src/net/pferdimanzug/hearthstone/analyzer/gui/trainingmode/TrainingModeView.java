package net.pferdimanzug.hearthstone.analyzer.gui.trainingmode;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;

public class TrainingModeView extends BorderPane implements EventHandler<ActionEvent> {

	@FXML
	protected Button backButton;

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

		backButton.setOnAction(this);

		resultChart.setVisible(false);
	}

	@Override
	public void handle(ActionEvent actionEvent) {

		if (actionEvent.getSource() == backButton) {
			ApplicationFacade.getInstance().sendNotification(GameNotification.MAIN_MENU);
		}
	}

	public void showProgress(TrainingProgressReport progress) {
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
