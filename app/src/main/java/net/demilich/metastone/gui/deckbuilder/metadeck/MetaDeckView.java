package net.demilich.metastone.gui.deckbuilder.metadeck;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.MetaDeck;
import net.demilich.metastone.gui.IconFactory;

public class MetaDeckView extends BorderPane {

	@FXML
	private Pane contentPane;

	public MetaDeckView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MetaDeckView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		setCache(true);
	}

	public void deckChanged(MetaDeck metaDeck) {
		for (Node node : contentPane.getChildren()) {
			Deck deck = (Deck) node.getUserData();
			node.setDisable(metaDeck.getDecks().contains(deck));
		}
	}

	public void displayDecks(List<Deck> decks) {
		contentPane.getChildren().clear();
		for (Deck deck : decks) {
			if (deck.isMetaDeck()) {
				continue;
			}
			ImageView graphic = new ImageView(IconFactory.getClassIcon(deck.getHeroClass()));
			graphic.setFitWidth(48);
			graphic.setFitHeight(48);
			Button deckButton = new Button(deck.getName(), graphic);
			deckButton.setMaxWidth(160);
			deckButton.setMinWidth(160);
			deckButton.setMaxHeight(120);
			deckButton.setMinHeight(120);
			deckButton.setWrapText(true);
			deckButton.setContentDisplay(ContentDisplay.LEFT);
			deckButton.setOnAction(event -> NotificationProxy.sendNotification(GameNotification.ADD_DECK_TO_META_DECK, deck));
			deckButton.setUserData(deck);
			contentPane.getChildren().add(deckButton);
		}
	}

}
