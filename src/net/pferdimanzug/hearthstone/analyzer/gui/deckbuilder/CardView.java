package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.gui.cards.CardTooltip;

public class CardView extends BorderPane implements EventHandler<MouseEvent> {

	@FXML
	private Pane contentPane;

	@FXML
	private Button previousButton;

	@FXML
	private Button nextButton;

	@FXML
	private Label pageLabel;

	private int offset;
	private final int rows = 4;
	private final int columns = 2;
	private final int cardDisplayCount = rows * columns;

	private List<Card> cards;
	private final List<CardTooltip> cardWidgets = new ArrayList<CardTooltip>(cardDisplayCount);

	public CardView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CardView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		setupCardWidgets();
		
		previousButton.setOnAction(actionEvent -> changeOffset(-cardDisplayCount));
		nextButton.setOnAction(actionEvent -> changeOffset(+cardDisplayCount));
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
		int lastIndex = Math.min(cards.size(), offset + cardDisplayCount);
		updatePageLabel();
		for (CardTooltip CardTooltip : cardWidgets) {
			CardTooltip.setVisible(false);
		}
		int widgetIndex = 0;
		for (int i = offset; i < lastIndex; i++) {
			Card card = cards.get(i);
			CardTooltip cardWidget = cardWidgets.get(widgetIndex++);
			cardWidget.setCard(null, card, null);
			cardWidget.setVisible(true);
		}
	}

	@Override
	public void handle(MouseEvent event) {
		CardTooltip source = (CardTooltip) event.getSource();
		Card card = source.getCard();
		ApplicationFacade.getInstance().sendNotification(GameNotification.ADD_CARD_TO_DECK, card);
	}
	
	private void setupCardWidgets() {
		int currentRow = 0;
		int currentColumn = 0;
		for (int i = 0; i < cardDisplayCount; i++) {
			CardTooltip cardWidget = new CardTooltip();
			
			cardWidget.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
			cardWidget.setScaleX(0.95);
			cardWidget.setScaleY(0.95);
			cardWidget.setScaleZ(0.95);
			//contentPane.add(cardWidget, currentColumn, currentRow);
			contentPane.getChildren().add(cardWidget);
			currentColumn++;
			if (currentColumn == columns) {
				currentColumn = 0;
				currentRow++;
			}
			cardWidgets.add(cardWidget);
		}
	}

	private void updatePageLabel() {
		int totalPages = (int) Math.ceil(cards.size() / (double)cardDisplayCount);
		int currentPage = (int) Math.ceil(offset / (double)cardDisplayCount) + 1;
		pageLabel.setText(currentPage + "/" + totalPages);
	}

}
