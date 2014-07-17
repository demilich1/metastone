package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;

public class CardFilterView extends HBox implements ChangeListener<String> {
	
	@FXML
	private TextField searchField;
	
	public CardFilterView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CardFilterView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		searchField.textProperty().addListener(this);
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		ApplicationFacade.getInstance().sendNotification(GameNotification.FILTER_CARDS_BY_TEXT, newValue);
	}

}
