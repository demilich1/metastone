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
import net.pferdimanzug.hearthstone.analyzer.game.decks.MetaDeck;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.gui.dialog.DialogNotification;
import net.pferdimanzug.hearthstone.analyzer.gui.dialog.DialogResult;
import net.pferdimanzug.hearthstone.analyzer.gui.dialog.DialogType;
import net.pferdimanzug.hearthstone.analyzer.gui.dialog.IDialogListener;

public class DeckInfoView extends HBox implements EventHandler<ActionEvent>, IDialogListener {

	@FXML
	private Button doneButton;

	@FXML
	private Label typeLabel;
	
	@FXML
	private Label countLabel;

	private Deck activeDeck;

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
		if (activeDeck.isMetaDeck() && !activeDeck.isComplete()) {
			DialogNotification dialogNotification = new DialogNotification("Warning",
					"Your deck collection is not complete yet. Each deck collection has to contain at least 2 (or more) decks. ", DialogType.WARNING);
			ApplicationFacade.getInstance().notifyObservers(dialogNotification);
		}
		else if (!activeDeck.isMetaDeck() && !activeDeck.isComplete()) {
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
		this.activeDeck = deck;
		if (deck.isMetaDeck()) {
			MetaDeck metaDeck = (MetaDeck) deck;
			typeLabel.setText("Decks");
			countLabel.setText(metaDeck.getDecks().size() + "");
		} else {
			typeLabel.setText("Cards");
			countLabel.setText(deck.getCards().getCount() + "/" + GameLogic.DECK_SIZE);	
		}
		
	}

}
