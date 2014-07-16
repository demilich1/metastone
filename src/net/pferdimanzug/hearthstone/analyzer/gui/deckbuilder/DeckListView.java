package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;

public class DeckListView extends VBox {

	@FXML
	private Button newDeckButton;

	public DeckListView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeckListView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		newDeckButton.setOnAction(actionEvent -> ApplicationFacade.getInstance().sendNotification(GameNotification.CREATE_NEW_DECK));
	}
	
	public void displayDecks(List<Deck> decks) {
		getChildren().clear();
		getChildren().add(newDeckButton);
		for (Deck deck : decks) {
			DeckEntry deckEntry = new DeckEntry();
			deckEntry.setDeck(deck);
			getChildren().add(deckEntry);
		}
	}

}
