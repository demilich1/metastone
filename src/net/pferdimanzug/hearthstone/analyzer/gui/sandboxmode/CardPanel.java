package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode;

import java.io.IOException;

import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class CardPanel extends VBox {

	@FXML
	private Button editHandButton;

	@FXML
	private Button editDeckButton;

	private Player selectedPlayer;

	public CardPanel() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CardPanel.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		editHandButton.setOnAction(this::handleEditHandButton);
		editDeckButton.setOnAction(this::handleEditDeckButton);
	}

	public void onPlayerSelectionChanged(Player selectedPlayer) {
		this.selectedPlayer = selectedPlayer;
		editHandButton.setDisable(selectedPlayer == null);
		editDeckButton.setDisable(selectedPlayer == null);
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

	private void onDeckFinishedEditing(CardCollection cardCollection) {
		ApplicationFacade.getInstance().sendNotification(GameNotification.MODIFY_PLAYER_DECK, cardCollection);
	}

	private void onHandFinishedEditing(CardCollection cardCollection) {
		ApplicationFacade.getInstance().sendNotification(GameNotification.MODIFY_PLAYER_HAND, cardCollection);
	}

}
