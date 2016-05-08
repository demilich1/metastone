package net.demilich.metastone.gui.simulationmode;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;

public class WaitForSimulationView extends BorderPane {

	@FXML
	private ProgressBar progressBar;

	@FXML
	private Label gamesCompletedLabel;

	public WaitForSimulationView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/WaitForSimulationView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

	}

	public void update(int gamesCompleted, int gamesTotal) {
		gamesCompletedLabel.setText(gamesCompleted + "/" + gamesTotal + " games completed");
		progressBar.setProgress(gamesCompleted / (double) gamesTotal);
	}

}
