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
import javafx.scene.control.ToolBar;
import javafx.util.StringConverter;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.actions.EditPropertiesAction;

public class ToolboxView extends ToolBar {

	@FXML
	private ChoiceBox<Player> playerChoiceBox;

	@FXML
	private Button editEntityButton;
	
	@FXML
	private Button editHandButton;

	@FXML
	private Button editDeckButton;
	
	private final MinionPanel minionPanel;

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
		
		editEntityButton.setOnAction(this::handleEditEntityButton);
		
		minionPanel = new MinionPanel();
		getItems().add(minionPanel);
	}
	
	private void handleEditEntityButton(ActionEvent actionEvent) {
		EditPropertiesAction editAction = new EditPropertiesAction();
		ApplicationFacade.getInstance().sendNotification(GameNotification.PERFORM_ACTION, editAction);
	}

	private void handleEditDeckButton(ActionEvent actionEvent) {
		CardCollection deck = selectedPlayer.getDeck();
		CardCollectionEditor cardCollectionEditor = new CardCollectionEditor("Edit deck", deck, this::onDeckFinishedEditing,
				GameLogic.DECK_SIZE);
		ApplicationFacade.getInstance().sendNotification(GameNotification.SHOW_MODAL_DIALOG, cardCollectionEditor);
	}

	private void handleEditHandButton(ActionEvent actionEvent) {
		CardCollection hand = selectedPlayer.getHand();
		CardCollectionEditor cardCollectionEditor = new CardCollectionEditor("Edit hand", hand, this::onHandFinishedEditing,
				GameLogic.MAX_HAND_CARDS);
		ApplicationFacade.getInstance().sendNotification(GameNotification.SHOW_MODAL_DIALOG, cardCollectionEditor);
	}

	public void handlePlayerChanged(ObservableValue<? extends Player> ov, Player oldSelected, Player newSelected) {
		selectedPlayer = newSelected;
		editHandButton.setDisable(selectedPlayer == null);
		editDeckButton.setDisable(selectedPlayer == null);
		ApplicationFacade.getInstance().sendNotification(GameNotification.SELECT_PLAYER, selectedPlayer);
	}

	private void onDeckFinishedEditing(CardCollection cardCollection) {
		ApplicationFacade.getInstance().sendNotification(GameNotification.MODIFY_PLAYER_DECK, cardCollection);
	}

	private void onHandFinishedEditing(CardCollection cardCollection) {
		ApplicationFacade.getInstance().sendNotification(GameNotification.MODIFY_PLAYER_HAND, cardCollection);
	}

	public void setContext(GameContext context) {
		if (playerChoiceBox.getSelectionModel().isEmpty()) {
			ObservableList<Player> players = FXCollections.observableArrayList();
			players.addAll(context.getPlayers());
			playerChoiceBox.setItems(players);
			playerChoiceBox.getSelectionModel().selectFirst();
		}
		minionPanel.setContext(context);
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
