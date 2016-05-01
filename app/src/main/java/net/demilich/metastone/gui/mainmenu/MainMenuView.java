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
import net.demilich.metastone.BuildConfig;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;

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

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainMenuView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		deckBuilderButton.setOnAction(event -> NotificationProxy.sendNotification(GameNotification.DECK_BUILDER_SELECTED));

		playModeButton.setOnAction(event -> NotificationProxy.sendNotification(GameNotification.PLAY_MODE_SELECTED));

		simulationModeButton
				.setOnAction(event -> NotificationProxy.sendNotification(GameNotification.SIMULATION_MODE_SELECTED));

		sandboxModeButton.setOnAction(event -> NotificationProxy.sendNotification(GameNotification.SANDBOX_MODE_SELECTED));

		trainingModeButton.setOnAction(event -> NotificationProxy.sendNotification(GameNotification.TRAINING_MODE_SELECTED));

		battleOfDecksButton
				.setOnAction(event -> NotificationProxy.sendNotification(GameNotification.BATTLE_OF_DECKS_SELECTED));

		if (!BuildConfig.DEV_BUILD) {
			trainingModeButton.setVisible(false);
			trainingModeButton.setManaged(false);
			battleOfDecksButton.setVisible(false);
			battleOfDecksButton.setManaged(false);
		}

		versionLabel.setText(BuildConfig.VERSION + (BuildConfig.DEV_BUILD ? " (Dev build)" : ""));

		donationButton.setOnAction(this::openDonation);
	}

	private void openDonation(ActionEvent event) {
		try {
			java.awt.Desktop.getDesktop()
					.browse(new URI("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=92DYWPZUVDMEY"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
