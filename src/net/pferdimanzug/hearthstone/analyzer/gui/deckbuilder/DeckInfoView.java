package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.gui.dialog.DialogNotification;
import net.pferdimanzug.hearthstone.analyzer.gui.dialog.DialogResult;
import net.pferdimanzug.hearthstone.analyzer.gui.dialog.DialogType;
import net.pferdimanzug.hearthstone.analyzer.gui.dialog.IDialogListener;

public class DeckInfoView extends HBox implements EventHandler<ActionEvent>, IDialogListener {

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

	@Override
	public void handle(ActionEvent event) {
		if (!deckComplete) {
			DialogNotification dialogNotification = new DialogNotification("Add random cards",
					"Your deck is not complete yet. If you proceed, all open slots will be filled with random cards.", DialogType.CONFIRM);
			dialogNotification.setHandler(this);
			ApplicationFacade.getInstance().notifyObservers(dialogNotification);
		} else {
			ApplicationFacade.getInstance().sendNotification(GameNotification.SAVE_ACTIVE_DECK);
		}

	}

	@Override
	public void onDialogClosed(DialogResult result) {
		if (result == DialogResult.OK) {
			ApplicationFacade.getInstance().sendNotification(GameNotification.FILL_DECK_WITH_RANDOM_CARDS);
			ApplicationFacade.getInstance().sendNotification(GameNotification.SAVE_ACTIVE_DECK);
		}
	}

	public void updateDeck(Deck deck) {
		deckComplete = deck.isComplete();
		cardCountLabel.setText(deck.getCards().getCount() + "/" + GameLogic.DECK_SIZE);
	}

}
