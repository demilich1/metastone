package net.demilich.metastone.gui.deckbuilder;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import net.demilich.metastone.ApplicationFacade;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.gui.IconFactory;
import net.demilich.metastone.gui.dialog.DialogNotification;
import net.demilich.metastone.gui.dialog.DialogResult;
import net.demilich.metastone.gui.dialog.DialogType;

public class DeckEntry extends HBox {

	@FXML
	private Label deckNameLabel;

	@FXML
	private ImageView classIcon;

	@FXML
	private Button deleteDeckButton;

	private Deck deck;

	public DeckEntry() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DeckEntry.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		deleteDeckButton.setOnAction(this::handleDeleteDeck);
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
		deckNameLabel.setText(deck.getName());
		classIcon.setImage(IconFactory.getClassIcon(deck.getHeroClass()));
	}

	private void handleDeleteDeck(ActionEvent event) {
		DialogNotification dialogNotification = new DialogNotification("Delete deck",
				"Do you really want to delete the deck '" + deck.getName() + "'? This cannot be undone.", DialogType.WARNING);
		dialogNotification.setHandler(this::onDeleteDeckDialog);
		ApplicationFacade.getInstance().notifyObservers(dialogNotification);
	}

	private void onDeleteDeckDialog(DialogResult result) {
		if (result == DialogResult.OK) {
			NotificationProxy.sendNotification(GameNotification.DELETE_DECK, deck);
		}
	}

}
