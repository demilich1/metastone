package net.demilich.metastone.gui.mainmenu;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import net.demilich.metastone.AppConfig;
import net.demilich.metastone.ApplicationFacade;
import net.demilich.metastone.GameNotification;

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
	
	@FXML
	private Button donationButton;

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
		
		if (!AppConfig.DEV_BUILD) {
			trainingModeButton.setVisible(false);
			trainingModeButton.setManaged(false);
		}
		
		versionLabel.setText(AppConfig.VERSION + (AppConfig.DEV_BUILD ? " (Dev build)" : ""));
		
		donationButton.setOnAction(this::openDonation);
	}
	
	private void openDonation(ActionEvent event) {
		try {
			java.awt.Desktop.getDesktop().browse(new URI("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=92DYWPZUVDMEY"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
