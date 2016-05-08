package net.demilich.metastone.gui.sandboxmode;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class SandboxEditor extends BorderPane {

	@FXML
	protected Label headerLabel;

	@FXML
	protected Button okButton;

	@FXML
	protected Button cancelButton;

	public SandboxEditor(String fxmlFile) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFile));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	protected void setTitle(String title) {
		headerLabel.setText(title);
	}
}
