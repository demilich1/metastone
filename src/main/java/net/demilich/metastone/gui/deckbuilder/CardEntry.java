package net.demilich.metastone.gui.deckbuilder;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import net.demilich.metastone.game.cards.Card;

public class CardEntry extends HBox {

	@FXML
	private Label cardNameLabel;

	@FXML
	private Text manaCostText;

	@FXML
	private Text countText;

	private int stack;

	private Card card;

	public CardEntry() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CardEntry.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		setCache(true);
	}

	public void addCard(Card card) {
		this.card = card;
		cardNameLabel.setText(card.getName());
		manaCostText.setText(String.valueOf(card.getBaseManaCost()));
		stack++;
		countText.setText(String.valueOf(stack));
		countText.setVisible(stack > 1);
	}

	public Card getCard() {
		return card;
	}

	public void resetStackCount() {
		stack = 0;
	}
}
