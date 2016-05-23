package net.demilich.metastone.gui.deckbuilder;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import net.demilich.metastone.ApplicationFacade;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.MetaDeck;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.gui.dialog.DialogNotification;
import net.demilich.metastone.gui.dialog.DialogResult;
import net.demilich.metastone.gui.dialog.DialogType;
import net.demilich.metastone.gui.dialog.IDialogListener;

public class DeckInfoView extends HBox implements EventHandler<ActionEvent>, IDialogListener {

	@FXML
	private Button doneButton;

	@FXML
	private Label typeLabel;

	@FXML
	private Label countLabel;

	private Deck activeDeck;

	public DeckInfoView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DeckInfoView.fxml"));
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
					"Your deck collection is not complete yet. Each deck collection has to contain at least 2 (or more) decks. ",
					DialogType.WARNING);
			ApplicationFacade.getInstance().notifyObservers(dialogNotification);
		} else if (!activeDeck.isMetaDeck() && !activeDeck.isComplete() && !activeDeck.isTooBig() && !activeDeck.isArbitrary()) {
			DialogNotification dialogNotification = new DialogNotification("Add random cards",
					"Your deck is not complete yet. If you proceed, all open slots will be filled with random cards.", DialogType.CONFIRM);
			dialogNotification.setHandler(this);
			ApplicationFacade.getInstance().notifyObservers(dialogNotification);
		} else if (!activeDeck.isMetaDeck() && !activeDeck.isComplete() && activeDeck.isTooBig() && !activeDeck.isArbitrary()) { 
			DialogNotification dialogNotification = new DialogNotification("Remove random cards",
					"Your deck has too many cards. If you proceed, some cards will be removed at random.", DialogType.CONFIRM);
			dialogNotification.setHandler(this);
			ApplicationFacade.getInstance().notifyObservers(dialogNotification);
		} else {
			NotificationProxy.sendNotification(GameNotification.SAVE_ACTIVE_DECK);
		}

	}

	@Override
	public void onDialogClosed(DialogResult result) {
		if (result == DialogResult.OK) {
			NotificationProxy.sendNotification(GameNotification.FILL_DECK_WITH_RANDOM_CARDS);
			NotificationProxy.sendNotification(GameNotification.SAVE_ACTIVE_DECK);
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
			if (deck.isTooBig()) {
				countLabel.setText(deck.getCards().getCount() + "!/" + GameLogic.DECK_SIZE);
				countLabel.setTextFill(Color.RED);
			} else {
				countLabel.setText(deck.getCards().getCount() + "/" + GameLogic.DECK_SIZE);
				countLabel.setTextFill(Color.BLACK);
			}
		}

	}

}
