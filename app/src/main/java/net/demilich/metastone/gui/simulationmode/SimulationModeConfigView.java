package net.demilich.metastone.gui.simulationmode;

import java.io.IOException;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import net.demilich.metastone.ApplicationFacade;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.gui.common.DeckFormatStringConverter;
import net.demilich.metastone.game.gameconfig.GameConfig;

public class SimulationModeConfigView extends BorderPane implements EventHandler<ActionEvent> {

	@FXML
	protected ComboBox<DeckFormat> formatBox;

	@FXML
	protected HBox playerArea;

	@FXML
	protected Button startButton;

	@FXML
	protected Button backButton;

	@FXML
	protected ComboBox<Integer> numberOfGamesBox;

	protected PlayerConfigView player1Config;
	protected PlayerConfigView player2Config;

	private List<DeckFormat> deckFormats;

	public SimulationModeConfigView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SimulationModeConfigView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		formatBox.setConverter(new DeckFormatStringConverter());

		player1Config = new PlayerConfigView();
		player2Config = new PlayerConfigView();

		playerArea.getChildren().add(player1Config);
		playerArea.getChildren().add(player2Config);

		startButton.setOnAction(this);
		backButton.setOnAction(this);
		setupNumberOfGamesBox();

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
			gameConfig.setNumberOfGames(numberOfGamesBox.getSelectionModel().getSelectedItem());
			gameConfig.setPlayerConfig1(player1Config.getPlayerConfig());
			gameConfig.setPlayerConfig2(player2Config.getPlayerConfig());
			gameConfig.setDeckFormat(formatBox.getValue());
			ApplicationFacade.getInstance().sendNotification(GameNotification.COMMIT_SIMULATIONMODE_CONFIG, gameConfig);
		} else if (actionEvent.getSource() == backButton) {
			ApplicationFacade.getInstance().sendNotification(GameNotification.MAIN_MENU);
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

	private void setupNumberOfGamesBox() {
		ObservableList<Integer> numberOfGamesEntries = FXCollections.observableArrayList();
		numberOfGamesEntries.add(1);
		numberOfGamesEntries.add(10);
		numberOfGamesEntries.add(100);
		numberOfGamesEntries.add(1000);
		numberOfGamesEntries.add(10000);
		numberOfGamesEntries.add(100000);
		numberOfGamesBox.setItems(numberOfGamesEntries);
		numberOfGamesBox.getSelectionModel().select(2);
	}

}
