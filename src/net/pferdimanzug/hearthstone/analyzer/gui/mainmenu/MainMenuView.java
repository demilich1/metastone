package net.pferdimanzug.hearthstone.analyzer.gui.mainmenu;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import net.pferdimanzug.hearthstone.analyzer.AppConfig;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;

public class MainMenuView extends BorderPane {

	@FXML
	private Button deckBuilderButton;

	@FXML
	private Button playModeButton;

	@FXML
	private Button simulationModeButton;
	
	@FXML
	private Button sandboxModeButton;
	
	@FXML
	private Button trainingModeButton;
	
	@FXML
	private Button battleOfDecksButton;
	
	@FXML
	private Label versionLabel;

	public MainMenuView() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		deckBuilderButton.setOnAction(event -> ApplicationFacade.getInstance().sendNotification(GameNotification.DECK_BUILDER_SELECTED));
		
		playModeButton.setOnAction(event -> ApplicationFacade.getInstance().sendNotification(GameNotification.PLAY_MODE_SELECTED));
		
		simulationModeButton.setOnAction(event -> ApplicationFacade.getInstance().sendNotification(
				GameNotification.SIMULATION_MODE_SELECTED));
		
		sandboxModeButton.setOnAction(event -> ApplicationFacade.getInstance().sendNotification(
				GameNotification.SANDBOX_MODE_SELECTED));
		
		trainingModeButton.setOnAction(event -> ApplicationFacade.getInstance().sendNotification(
				GameNotification.TRAINING_MODE_SELECTED));
		
		battleOfDecksButton.setOnAction(event -> ApplicationFacade.getInstance().sendNotification(
				GameNotification.BATTLE_OF_DECKS_SELECTED));
		
		trainingModeButton.setVisible(false);
		trainingModeButton.setManaged(false);
		
		versionLabel.setText(AppConfig.VERSION);
	}

}
