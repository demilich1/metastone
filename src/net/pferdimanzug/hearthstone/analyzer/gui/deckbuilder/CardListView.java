package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.util.HashMap;

import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;

public class CardListView extends VBox {

	private final HashMap<Integer, CardEntry> existingCardEntries = new HashMap<Integer, CardEntry>();

	public CardListView() {
		super(2);
		this.setAlignment(Pos.TOP_LEFT);
		this.setPrefSize(240, USE_COMPUTED_SIZE);
		// setPadding(new Insets(4, 0, 4, 8));
	}

	public void displayDeck(Deck deck) {
		existingCardEntries.clear();
		getChildren().clear();
		for (Card card : deck.getCards()) {
			Integer cardTypeId = card.getTypeId();
			CardEntry cardEntry = null;
			if (existingCardEntries.containsKey(cardTypeId)) {
				cardEntry = existingCardEntries.get(cardTypeId);
				cardEntry.addCard(card);
			} else {
				cardEntry = new CardEntry(card);
				cardEntry.addEventHandler(MouseEvent.MOUSE_CLICKED,
						mouseEvent -> ApplicationFacade.getInstance().sendNotification(GameNotification.REMOVE_CARD_FROM_DECK, card));
				getChildren().add(cardEntry);
				existingCardEntries.put(cardTypeId, cardEntry);
			}

		}
	}

}
