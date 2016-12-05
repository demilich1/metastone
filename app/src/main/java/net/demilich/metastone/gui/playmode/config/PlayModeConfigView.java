package net.demilich.metastone.gui.playmode.config;

import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.common.MatchmakingRequest;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import net.demilich.metastone.BuildConfig;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.gameconfig.GameConfig;
import net.demilich.metastone.gui.common.DeckFormatStringConverter;
import net.demilich.metastone.gui.gameconfig.PlayerConfigView;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayModeConfigView extends BorderPane implements EventHandler<ActionEvent> {
	private static final String sessionId = RandomStringUtils.randomAlphanumeric(36);
	@FXML
	protected ComboBox<DeckFormat> formatBox;

	@FXML
	protected HBox playerArea;

	@FXML
	protected Button startButton;

	@FXML
	protected Button backButton;

	protected PlayerConfigView player1Config;
	protected PlayerConfigView player2Config;


	private boolean isMultiplayer;

	private List<DeckFormat> deckFormats;

	public PlayModeConfigView(boolean isMultiplayer) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PlayModeConfigView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		this.isMultiplayer = isMultiplayer;

		formatBox.setConverter(new DeckFormatStringConverter());

		player1Config = new PlayerConfigView(PlayerConfigType.HUMAN);

		if (isMultiplayer) {
			player1Config.hideBehaviours();
			player1Config.hideHideCards();
		}

		player2Config = new PlayerConfigView(PlayerConfigType.OPPONENT);

		playerArea.getChildren().add(player1Config);

		if (!isMultiplayer) {
			playerArea.getChildren().add(player2Config);
		}

		startButton.setOnAction(this);
		backButton.setOnAction(this);

		formatBox.valueProperty().addListener((ChangeListener<DeckFormat>) (observableProperty, oldDeckFormat, newDeckFormat) -> {
			setDeckFormats(newDeckFormat);
		});
	}

	private void setupDeckFormats() {
		ObservableList<DeckFormat> deckFormatList = FXCollections.observableArrayList();

		for (DeckFormat deckFormat : deckFormats) {
			deckFormatList.add(deckFormat);
		}

		formatBox.setItems(deckFormatList);
		formatBox.getSelectionModel().selectFirst();
	}

	private void setDeckFormats(DeckFormat newDeckFormat) {
		player1Config.setDeckFormat(newDeckFormat);
		player2Config.setDeckFormat(newDeckFormat);
	}

	@Override
	public void handle(ActionEvent actionEvent) {
		if (actionEvent.getSource() == startButton) {
			GameConfig gameConfig = new GameConfig();
			gameConfig.setNumberOfGames(1);
			gameConfig.setPlayerConfig1(player1Config.getPlayerConfig());
			gameConfig.setPlayerConfig2(player2Config.getPlayerConfig());
			gameConfig.setDeckFormat(formatBox.getValue());

			if (isMultiplayer) {
				Alert dialog = new Alert(Alert.AlertType.INFORMATION,
						"Finding another player...",
						ButtonType.CANCEL);

				dialog.setTitle("Matchmaking");
				dialog.setHeaderText(null);

				MatchmakingTask matchmaking = new MatchmakingTask(sessionId, player1Config.getPlayerConfig().getDeck());

				matchmaking.setOnSucceeded(e -> {
					dialog.close();
				});

				matchmaking.setOnFailed(e -> {
					dialog.close();
				});

				new Thread(matchmaking).start();
				dialog.showAndWait();
				matchmaking.stop();

				ClientConnectionConfiguration clientConnectionConfiguration = matchmaking.getConnection();

				if (clientConnectionConfiguration != null) {
					// TODO: The matchmaker should really do its best to return the correct public URL
					gameConfig.setConnection(new ClientConnectionConfiguration(
							BuildConfig.GAMESESSIONS_HOST,
							clientConnectionConfiguration.getPort(),
							clientConnectionConfiguration.getFirstMessage()
					));
				} else {
					// Do nothing and cancel.
					return;
				}
			}
			gameConfig.setMultiplayer(this.isMultiplayer);
			NotificationProxy.sendNotification(GameNotification.COMMIT_PLAYMODE_CONFIG, gameConfig);
		} else if (actionEvent.getSource() == backButton) {
			NotificationProxy.sendNotification(GameNotification.MAIN_MENU);
		}
	}


	public void injectDecks(List<Deck> decks) {
		player1Config.injectDecks(decks);
		player2Config.injectDecks(decks);
	}

	public void injectDeckFormats(List<DeckFormat> deckFormats) {
		this.deckFormats = deckFormats;
		setupDeckFormats();
		player1Config.setDeckFormat(formatBox.getValue());
		player2Config.setDeckFormat(formatBox.getValue());
	}

}
