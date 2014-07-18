package net.pferdimanzug.hearthstone.analyzer.gui.mainmenu;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;

public class MainMenuView extends BorderPane {

	@FXML
	private Button deckBuilderButton;

	@FXML
	private Button playModeButton;
	
	@FXML
	private Button simulationModeButton;

	public MainMenuView() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		deckBuilderButton.setOnAction(actionEvent -> ApplicationFacade.getInstance().sendNotification(
				GameNotification.DECK_BUILDER_SELECTED));
	}

}
