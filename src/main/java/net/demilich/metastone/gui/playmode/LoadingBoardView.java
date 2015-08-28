package net.demilich.metastone.gui.playmode;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;

public class LoadingBoardView extends BorderPane {

	@FXML
	private ProgressIndicator loadingIndicator;

	public LoadingBoardView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/LoadingBoardView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		loadingIndicator.setProgress(-1);
	}

}
