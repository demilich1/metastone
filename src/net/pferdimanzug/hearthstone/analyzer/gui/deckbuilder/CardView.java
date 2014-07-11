package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.HandCard;

public class CardView extends BorderPane {

	@FXML
	private GridPane contentPane;

	@FXML
	private Button previousButton;

	@FXML
	private Button nextButton;

	@FXML
	private Label pageLabel;

	private int offset;
	private final int rows = 3;
	private final int columns = 3;
	private final int cardDisplayCount = rows * columns;

	private List<Card> cards;

	public CardView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CardView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		previousButton.setOnAction(actionEvent->changeOffset(-cardDisplayCount));
		nextButton.setOnAction(actionEvent->changeOffset(+cardDisplayCount));
	}
	
	private void changeOffset(int delta) {
		int newOffset = offset + delta;
		if (newOffset < 0 || newOffset >= cards.size()) {
			return;
		}
		offset += delta;
		displayCurrentPage();
	}

	public void displayCards(List<Card> cards) {
		this.cards = cards;
		offset = 0;
		displayCurrentPage();
	}

	private void displayCurrentPage() {
		contentPane.getChildren().clear();
		int currentRow = 0;
		int currentColumn = 0;
		int lastIndex = Math.min(cards.size(), offset + cardDisplayCount);
		for (int i = offset; i < lastIndex; i++) {
			Card card = cards.get(i);
			HandCard cardWidget = new HandCard();
			cardWidget.setPrefSize(140, 180);
			cardWidget.setCard(null, card, null);
			contentPane.add(cardWidget, currentColumn, currentRow);
			currentColumn++;
			if (currentColumn == columns) {
				currentColumn = 0;
				currentRow++;
			}
		}
	}

}
