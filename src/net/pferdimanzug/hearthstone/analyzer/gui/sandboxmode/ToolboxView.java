package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.util.StringConverter;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCatalogue;

public class ToolboxView extends ToolBar {

	@FXML
	private ComboBox<Card> cardComboBox;

	public ToolboxView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ToolboxView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		cardComboBox.setConverter(new CardStringConverter());
		populateCardList();
	}

	private void populateCardList() {
		ObservableList<Card> cardList = FXCollections.observableArrayList();

		for (Card card : CardCatalogue.getAll()) {
			cardList.add(card);
		}

		cardComboBox.setItems(cardList);

	}

	private class CardStringConverter extends StringConverter<Card> {

		@Override
		public Card fromString(String arg0) {
			return null;
		}

		@Override
		public String toString(Card card) {
			String result = card.getName();
			result += "[" + card.getCardType() + "] ";
			result += "Mana: " + card.getBaseManaCost();
			return result;
		}

	}

}
