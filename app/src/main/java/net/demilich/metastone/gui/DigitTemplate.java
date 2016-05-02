package net.demilich.metastone.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class DigitTemplate extends HBox {

	@FXML
	public Text digit;

	public DigitTemplate() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DigitTemplate.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

	}
}
