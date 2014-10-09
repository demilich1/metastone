package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode;

import java.io.IOException;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.util.StringConverter;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCatalogue;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.actions.KillAction;

public class ToolboxView extends ToolBar {

	@FXML
	private ChoiceBox<Player> playerChoiceBox;

	@FXML
	private Button editHandButton;

	@FXML
	private Button editDeckButton;

	@FXML
	private ComboBox<MinionCard> minionComboBox;
	@FXML
	private TextField filterMinionsTextField;
	@FXML
	private Button spawnMinionButton;
	
	@FXML
	private Button killMinionButton;

	private Player selectedPlayer;

	public ToolboxView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ToolboxView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		editHandButton.setOnAction(this::handleEditHandButton);
		editDeckButton.setOnAction(this::handleEditDeckButton);

		playerChoiceBox.setConverter(new PlayerStringConverter());
		playerChoiceBox.getSelectionModel().selectedItemProperty().addListener(this::handlePlayerChanged);

		populateMinions(null);
		filterMinionsTextField.textProperty().addListener(this::onMinionFilterChanged);
		spawnMinionButton.setOnAction(this::handleSpawnMinionButton);
		killMinionButton.setOnAction(this::handleKillMinionButton);
	}

	private void populateMinions(String filter) {
		ObservableList<MinionCard> data = FXCollections.observableArrayList();
		for (Card card : CardCatalogue.getAll()) {
			if (card.getCardType() != CardType.MINION) {
				continue;
			}
			if (!card.matchesFilter(filter)) {
				continue;
			}
			MinionCard minionCard = (MinionCard) card;
			data.add(minionCard);
		}
		minionComboBox.setItems(data);
		minionComboBox.getSelectionModel().selectFirst();
		
		spawnMinionButton.setDisable(minionComboBox.getSelectionModel().getSelectedItem() == null);
	}

	public void setContext(GameContext context) {
		if (playerChoiceBox.getSelectionModel().isEmpty()) {
			ObservableList<Player> players = FXCollections.observableArrayList();
			players.addAll(context.getPlayers());
			playerChoiceBox.setItems(players);
			playerChoiceBox.getSelectionModel().selectFirst();
		}
		killMinionButton.setDisable(true);
		for (Player player : context.getPlayers()) {
			if (context.getMinionCount(player) > 0) {
				killMinionButton.setDisable(false);
				break;
			}
		}
	}
	
	private void onMinionFilterChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		populateMinions(newValue);
	}

	private void handleSpawnMinionButton(ActionEvent actionEvent) {
		MinionCard selectedMinion = minionComboBox.getSelectionModel().getSelectedItem();
		ApplicationFacade.getInstance().sendNotification(GameNotification.SPAWN_MINION, selectedMinion);
	}
	
	private void handleKillMinionButton(ActionEvent actionEvent) {
		KillAction killAction = new KillAction();
		ApplicationFacade.getInstance().sendNotification(GameNotification.PERFORM_ACTION, killAction);
	}

	private void handleEditHandButton(ActionEvent actionEvent) {
		CardCollection hand = selectedPlayer.getHand();
		CardCollectionEditor cardCollectionEditor = new CardCollectionEditor("Edit hand", hand, this::onHandFinishedEditing,
				GameLogic.MAX_HAND_CARDS);
		ApplicationFacade.getInstance().sendNotification(GameNotification.SHOW_MODAL_DIALOG, cardCollectionEditor);
	}

	private void onHandFinishedEditing(CardCollection cardCollection) {
		ApplicationFacade.getInstance().sendNotification(GameNotification.MODIFY_PLAYER_HAND, cardCollection);
	}

	private void handleEditDeckButton(ActionEvent actionEvent) {
		CardCollection deck = selectedPlayer.getDeck();
		CardCollectionEditor cardCollectionEditor = new CardCollectionEditor("Edit deck", deck, this::onDeckFinishedEditing,
				GameLogic.DECK_SIZE);
		ApplicationFacade.getInstance().sendNotification(GameNotification.SHOW_MODAL_DIALOG, cardCollectionEditor);
	}

	private void onDeckFinishedEditing(CardCollection cardCollection) {
		ApplicationFacade.getInstance().sendNotification(GameNotification.MODIFY_PLAYER_DECK, cardCollection);
	}

	public void handlePlayerChanged(ObservableValue<? extends Player> ov, Player oldSelected, Player newSelected) {
		selectedPlayer = newSelected;
		editHandButton.setDisable(selectedPlayer == null);
		editDeckButton.setDisable(selectedPlayer == null);
		ApplicationFacade.getInstance().sendNotification(GameNotification.SELECT_PLAYER, selectedPlayer);
	}

	private class PlayerStringConverter extends StringConverter<Player> {

		@Override
		public Player fromString(String arg0) {
			return null;
		}

		@Override
		public String toString(Player player) {
			return player.getName();
		}

	}

}
