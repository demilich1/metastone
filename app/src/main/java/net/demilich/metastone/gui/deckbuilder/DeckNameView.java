package net.demilich.metastone.gui.deckbuilder;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.gui.IconFactory;

public class DeckNameView extends HBox implements ChangeListener<String> {

	@FXML
	private ImageView classIcon;

	@FXML
	private TextField nameField;

	public DeckNameView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DeckNameView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		nameField.textProperty().addListener(this);
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		NotificationProxy.sendNotification(GameNotification.CHANGE_DECK_NAME, newValue);
	}

	public void updateDeck(Deck deck) {
		classIcon.setImage(IconFactory.getClassIcon(deck.getHeroClass()));
		nameField.setText(deck.getName());
	}

}
