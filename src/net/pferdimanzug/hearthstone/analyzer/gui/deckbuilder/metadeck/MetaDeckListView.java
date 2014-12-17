package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.metadeck;

import java.io.IOException;
import java.util.List;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.DeckEntry;

public class MetaDeckListView extends VBox implements EventHandler<MouseEvent> {

	@FXML
	private Button newDeckButton;

	public MetaDeckListView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MetaDeckListView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		setCache(true);
	}

	public void displayDecks(List<Deck> decks) {
		getChildren().clear();
		for (Deck deck : decks) {
			DeckEntry deckEntry = new DeckEntry();
			deckEntry.setDeck(deck);
			deckEntry.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
			getChildren().add(deckEntry);
		}
	}

	@Override
	public void handle(MouseEvent event) {
		DeckEntry deckEntry = (DeckEntry) event.getSource();
		ApplicationFacade.getInstance().sendNotification(GameNotification.REMOVE_DECK_FROM_META_DECK, deckEntry.getDeck());
	}

}
