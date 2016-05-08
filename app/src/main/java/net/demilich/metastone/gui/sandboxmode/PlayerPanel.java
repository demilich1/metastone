package net.demilich.metastone.gui.sandboxmode;

import java.io.IOException;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.gui.sandboxmode.actions.EditEntityAction;
import net.demilich.metastone.gui.sandboxmode.actions.SetManaAction;
import net.demilich.metastone.gui.sandboxmode.actions.SetMaxManaAction;

public class PlayerPanel extends VBox {

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

	@FXML
	private ComboBox<Player> playerComboBox;

	@FXML
	private Button editEntityButton;
	@FXML
	private ComboBox<Integer> currentManaBox;

	@FXML
	private ComboBox<Integer> maxManaBox;

	private boolean ignoreManaChange;

	private Player selectedPlayer;

	public PlayerPanel() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PlayerPanel.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		playerComboBox.setConverter(new PlayerStringConverter());
		playerComboBox.getSelectionModel().selectedItemProperty().addListener(this::handlePlayerChanged);

		currentManaBox.getSelectionModel().selectedItemProperty().addListener(this::handleCurrentManaChanged);
		maxManaBox.getSelectionModel().selectedItemProperty().addListener(this::handleMaxManaChanged);

		editEntityButton.setOnAction(this::handleEditEntityButton);
	}

	private void handleCurrentManaChanged(ObservableValue<? extends Number> ov, Number oldIndex, Number newIndex) {
		if (selectedPlayer == null || ignoreManaChange) {
			return;
		}
		Integer newValue = currentManaBox.getSelectionModel().getSelectedItem();
		SetManaAction setManaAction = new SetManaAction(selectedPlayer.getId(), newValue);
		NotificationProxy.sendNotification(GameNotification.PERFORM_ACTION, setManaAction);
	}

	private void handleEditEntityButton(ActionEvent actionEvent) {
		EditEntityAction editAction = new EditEntityAction();
		NotificationProxy.sendNotification(GameNotification.PERFORM_ACTION, editAction);
	}

	private void handleMaxManaChanged(ObservableValue<? extends Number> ov, Number oldIndex, Number newIndex) {
		if (selectedPlayer == null || ignoreManaChange) {
			return;
		}
		Integer newValue = maxManaBox.getSelectionModel().getSelectedItem();
		SetMaxManaAction setMaxManaAction = new SetMaxManaAction(selectedPlayer.getId(), newValue);
		NotificationProxy.sendNotification(GameNotification.PERFORM_ACTION, setMaxManaAction);
	}

	private void handlePlayerChanged(ObservableValue<? extends Player> ov, Player oldSelected, Player newSelected) {
		selectedPlayer = newSelected;
		NotificationProxy.sendNotification(GameNotification.SELECT_PLAYER, selectedPlayer);
		populateManaBoxes();
	}

	private void populateManaBoxes() {
		ignoreManaChange = true;
		currentManaBox.getItems().clear();
		for (int i = 0; i <= GameLogic.MAX_MANA; i++) {
			currentManaBox.getItems().add(i);
		}
		currentManaBox.autosize();
		maxManaBox.getItems().clear();
		for (int i = 0; i <= GameLogic.MAX_MANA; i++) {
			maxManaBox.getItems().add(i);
		}
		currentManaBox.getSelectionModel().select(selectedPlayer.getMana());
		maxManaBox.getSelectionModel().select(selectedPlayer.getMaxMana());
		ignoreManaChange = false;
	}

	public void setContext(GameContext context) {
		if (playerComboBox.getSelectionModel().isEmpty()) {
			ObservableList<Player> players = FXCollections.observableArrayList();
			players.addAll(context.getPlayers());
			playerComboBox.setItems(players);
			playerComboBox.getSelectionModel().selectFirst();
		}
		populateManaBoxes();
	}

}
