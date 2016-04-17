package net.demilich.metastone.gui.battleofdecks;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.BorderPane;
import net.demilich.metastone.ApplicationFacade;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.behaviour.IBehaviour;
import net.demilich.metastone.game.behaviour.PlayRandomBehaviour;
import net.demilich.metastone.game.behaviour.threat.GameStateValueBehaviour;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.gui.common.BehaviourStringConverter;
import net.demilich.metastone.gui.common.DeckStringConverter;

public class BattleOfDecksConfigView extends BorderPane {

	@FXML
	private ComboBox<Integer> numberOfGamesBox;
	@FXML
	private ComboBox<IBehaviour> behaviourBox;

	@FXML
	private ListView<Deck> selectedDecksListView;
	@FXML
	private ListView<Deck> availableDecksListView;

	@FXML
	private Button addButton;
	@FXML
	private Button removeButton;
	@FXML
	private Button startButton;
	@FXML
	private Button backButton;

	public BattleOfDecksConfigView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/BattleOfDecksConfigView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		setupBehaviourBox();
		setupNumberOfGamesBox();

		selectedDecksListView.setCellFactory(TextFieldListCell.forListView(new DeckStringConverter()));
		selectedDecksListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		availableDecksListView.setCellFactory(TextFieldListCell.forListView(new DeckStringConverter()));
		availableDecksListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		addButton.setOnAction(this::handleAddButton);
		removeButton.setOnAction(this::handleRemoveButton);

		backButton.setOnAction(event -> ApplicationFacade.getInstance().sendNotification(GameNotification.MAIN_MENU));
		startButton.setOnAction(this::handleStartButton);
	}

	private void handleAddButton(ActionEvent event) {
		Collection<Deck> selectedDecks = availableDecksListView.getSelectionModel().getSelectedItems();
		selectedDecksListView.getItems().addAll(selectedDecks);
		availableDecksListView.getItems().removeAll(selectedDecks);
	}

	private void handleRemoveButton(ActionEvent event) {
		Collection<Deck> selectedDecks = selectedDecksListView.getSelectionModel().getSelectedItems();
		availableDecksListView.getItems().addAll(selectedDecks);
		selectedDecksListView.getItems().removeAll(selectedDecks);
	}

	private void handleStartButton(ActionEvent event) {
		int numberOfGames = numberOfGamesBox.getSelectionModel().getSelectedItem();
		IBehaviour behaviour = behaviourBox.getSelectionModel().getSelectedItem();
		Collection<Deck> decks = selectedDecksListView.getItems();
		BattleConfig battleConfig = new BattleConfig(numberOfGames, behaviour, decks);
		ApplicationFacade.getInstance().sendNotification(GameNotification.COMMIT_BATTLE_OF_DECKS_CONFIG, battleConfig);
	}

	public void injectDecks(List<Deck> decks) {
		selectedDecksListView.getItems().clear();
		ObservableList<Deck> validDecks = FXCollections.observableArrayList();
		for (Deck deck : decks) {
			if (deck.getHeroClass() == HeroClass.MAGE) {
				continue;
			}
		}
		availableDecksListView.getItems().setAll(validDecks);
	}

	private void setupBehaviourBox() {
		behaviourBox.setConverter(new BehaviourStringConverter());
		behaviourBox.getItems().setAll(new GameStateValueBehaviour(), new PlayRandomBehaviour());
		behaviourBox.getSelectionModel().selectFirst();
	}

	private void setupNumberOfGamesBox() {
		ObservableList<Integer> numberOfGamesEntries = FXCollections.observableArrayList();
		numberOfGamesEntries.add(1);
		numberOfGamesEntries.add(10);
		numberOfGamesEntries.add(100);
		numberOfGamesEntries.add(1000);
		numberOfGamesBox.setItems(numberOfGamesEntries);
		numberOfGamesBox.getSelectionModel().select(2);
	}

	public void injectDeckFormats(List<DeckFormat> deckFormats) {
//		selectedDeckFormatsListView.getItems().clear();
//		ObservableList<DeckFormat> validDeckFormats = FXCollections.observableArrayList();
//		availableDeckFormatsListView.getItems().setAll(validDeckFormats);
	}

}
