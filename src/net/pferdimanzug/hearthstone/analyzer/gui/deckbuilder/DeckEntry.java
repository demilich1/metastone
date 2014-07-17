package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.gui.IconFactory;

public class DeckEntry extends HBox {
	
	@FXML
	private Label deckNameLabel;

	@FXML
	private ImageView classIcon;

	private Deck deck;
	
	public DeckEntry() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeckEntry.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public void setDeck(Deck deck) {
		this.deck = deck;
		deckNameLabel.setText(deck.getName());
		classIcon.setImage(IconFactory.getClassIcon(deck.getHeroClass()));
	}

}
