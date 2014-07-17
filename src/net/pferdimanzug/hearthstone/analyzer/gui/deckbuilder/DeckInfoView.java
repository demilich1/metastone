package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.io.IOException;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog.Actions;
import org.controlsfx.dialog.Dialogs;

import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DeckInfoView extends HBox implements EventHandler<ActionEvent> {

	@FXML
	private Button doneButton;

	@FXML
	private Label cardCountLabel;

	private boolean deckComplete;

	public DeckInfoView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeckInfoView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		doneButton.setOnAction(this);
	}

	public void updateDeck(Deck deck) {
		deckComplete = deck.isComplete();
		cardCountLabel.setText(deck.getCards().getCount() + "/" + GameLogic.DECK_SIZE);
	}

	@Override
	public void handle(ActionEvent event) {
		if (!deckComplete) {
			Action action = Dialogs.create().actions(Actions.NO, Actions.YES).message("Do you want to fill the deck with random cards?")
					.title("Confirm").showConfirm();
			if (action == Actions.NO) {
				return;
			}
			ApplicationFacade.getInstance().sendNotification(GameNotification.FILL_DECK_WITH_RANDOM_CARDS);
			
		}
		ApplicationFacade.getInstance().sendNotification(GameNotification.SAVE_ACTIVE_DECK);
	}

}
