package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.GameConfig;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.PlayerConfigView;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.config.PlayerConfigType;

public class SandboxModeConfigView extends BorderPane {

	@FXML
	protected HBox playerArea;

	@FXML
	protected Button startButton;

	@FXML
	protected Button backButton;

	protected PlayerConfigView player1Config;
	protected PlayerConfigView player2Config;

	public SandboxModeConfigView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SandboxModeConfigView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		player1Config = new PlayerConfigView(PlayerConfigType.SANDBOX);
		player2Config = new PlayerConfigView(PlayerConfigType.SANDBOX);

		playerArea.getChildren().add(player1Config);
		playerArea.getChildren().add(player2Config);

		startButton.setOnAction(this::handleStartButton);
		backButton.setOnAction(this::handleBackButton);
	}
	
	private void handleStartButton(ActionEvent event) {
		GameConfig gameConfig = new GameConfig();
		gameConfig.setNumberOfGames(1);
		gameConfig.setPlayerConfig1(player1Config.getPlayerConfig());
		gameConfig.setPlayerConfig2(player2Config.getPlayerConfig());
		ApplicationFacade.getInstance().sendNotification(GameNotification.COMMIT_SANDBOXMODE_CONFIG, gameConfig);
	}
	
	private void handleBackButton(ActionEvent event) {
		ApplicationFacade.getInstance().sendNotification(GameNotification.MAIN_MENU);
	}

	public void injectDecks(List<Deck> decks) {
		player1Config.injectDecks(decks);
		player2Config.injectDecks(decks);
	}

}
