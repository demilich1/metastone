package net.pferdimanzug.hearthstone.analyzer.gui.playmode.config;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.GameConfig;

public class PlayModeSetupView extends BorderPane implements EventHandler<ActionEvent> {

	@FXML
	private HBox playerArea;

	@FXML
	private Button startButton;
	
	@FXML
	private Button backButton;

	private PlayerConfigView player1Config;
	private PlayerConfigView player2Config;

	public PlayModeSetupView() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayModeSetupView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		player1Config = new PlayerConfigView(PreselectionHint.HUMAN);
		player2Config = new PlayerConfigView(PreselectionHint.CPU);

		playerArea.getChildren().add(player1Config);
		playerArea.getChildren().add(player2Config);

		startButton.setOnAction(this);
		backButton.setOnAction(this);
	}

	public void injectDecks(List<Deck> decks) {
		player1Config.injectDecks(decks);
		player2Config.injectDecks(decks);
	}

	@Override
	public void handle(ActionEvent actionEvent) {
		if (actionEvent.getSource() == startButton) {
			GameConfig gameConfig = new GameConfig();
			gameConfig.setNumberOfGames(1);
			gameConfig.setPlayerConfig1(player1Config.getPlayerConfig());
			gameConfig.setPlayerConfig2(player2Config.getPlayerConfig());
			ApplicationFacade.getInstance().sendNotification(GameNotification.COMMIT_PLAYMODE_CONFIG, gameConfig);
		} else if (actionEvent.getSource() == backButton) {
			ApplicationFacade.getInstance().sendNotification(GameNotification.MAIN_MENU);
		}
	}

}
