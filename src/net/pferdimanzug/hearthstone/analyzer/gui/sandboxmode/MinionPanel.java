package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode;

import java.io.IOException;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCatalogue;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.actions.KillAction;

public class MinionPanel extends VBox {
	
	@FXML
	private ComboBox<MinionCard> minionComboBox;
	@FXML
	private TextField filterMinionsTextField;
	@FXML
	private Button spawnMinionButton;
	@FXML
	private Button killMinionButton;
	
	public MinionPanel() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MinionPanel.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		populateMinions(null);
		filterMinionsTextField.textProperty().addListener(this::onMinionFilterChanged);
		spawnMinionButton.setOnAction(this::handleSpawnMinionButton);
		killMinionButton.setOnAction(this::handleKillMinionButton);
	}
	
	private void handleKillMinionButton(ActionEvent actionEvent) {
		KillAction killAction = new KillAction();
		ApplicationFacade.getInstance().sendNotification(GameNotification.PERFORM_ACTION, killAction);
	}
	
	private void handleSpawnMinionButton(ActionEvent actionEvent) {
		MinionCard selectedMinion = minionComboBox.getSelectionModel().getSelectedItem();
		ApplicationFacade.getInstance().sendNotification(GameNotification.SPAWN_MINION, selectedMinion);
	}
	
	private void onMinionFilterChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		populateMinions(newValue);
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
		killMinionButton.setDisable(true);
		for (Player player : context.getPlayers()) {
			if (context.getMinionCount(player) > 0) {
				killMinionButton.setDisable(false);
				break;
			}
		}
	}

}
