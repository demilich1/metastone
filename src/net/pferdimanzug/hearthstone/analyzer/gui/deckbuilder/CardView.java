package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.io.IOException;
import java.util.List;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.CardTokenFactory;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.HandCard;

public class CardView extends BorderPane implements EventHandler<MouseEvent> {

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
	private final CardTokenFactory cardTokenFactory = new CardTokenFactory();

	public CardView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CardView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		previousButton.setOnAction(actionEvent -> changeOffset(-cardDisplayCount));
		nextButton.setOnAction(actionEvent -> changeOffset(+cardDisplayCount));
		setCache(true);
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
		clearChildren();
		int currentRow = 0;
		int currentColumn = 0;
		int lastIndex = Math.min(cards.size(), offset + cardDisplayCount);
		updatePageLabel();
		for (int i = offset; i < lastIndex; i++) {
			Card card = cards.get(i);
			HandCard cardWidget = cardTokenFactory.createHandCard(null, card, null);
			cardWidget.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
			cardWidget.setPrefSize(140, 180);
			contentPane.add(cardWidget, currentColumn, currentRow);
			currentColumn++;
			if (currentColumn == columns) {
				currentColumn = 0;
				currentRow++;
			}
		}
	}
	
	private void updatePageLabel() {
		int totalPages = (int) Math.ceil(cards.size() / (double)cardDisplayCount);
		int currentPage = (int) Math.ceil(offset / (double)cardDisplayCount) + 1;
		pageLabel.setText(currentPage + "/" + totalPages);
	}

	private void clearChildren() {
		for (Node child : contentPane.getChildren()) {
			child.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
		}
		contentPane.getChildren().clear();
	}

	@Override
	public void handle(MouseEvent event) {
		HandCard source = (HandCard) event.getSource();
		Card card = source.getCard();
		ApplicationFacade.getInstance().sendNotification(GameNotification.ADD_CARD_TO_DECK, card);
	}

}
