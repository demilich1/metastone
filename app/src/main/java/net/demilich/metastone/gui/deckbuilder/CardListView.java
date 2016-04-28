package net.demilich.metastone.gui.deckbuilder;

import java.util.HashMap;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.decks.Deck;

public class CardListView extends VBox implements EventHandler<MouseEvent> {

	private final HashMap<String, CardEntry> existingCardEntries = new HashMap<String, CardEntry>();
	private final CardEntryFactory cardEntryFactory = new CardEntryFactory();

	public CardListView() {
		super(2);
		this.setAlignment(Pos.TOP_LEFT);
		this.setPrefSize(240, USE_COMPUTED_SIZE);
	}

	private void clearChildren() {
		for (Node child : getChildren()) {
			child.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
		}
		getChildren().clear();
	}

	public void displayDeck(Deck deck) {
		existingCardEntries.clear();
		clearChildren();
		for (Card card : deck.getCards()) {
			String cardId = card.getCardId();
			CardEntry cardEntry = null;
			if (existingCardEntries.containsKey(cardId)) {
				cardEntry = existingCardEntries.get(cardId);
				cardEntry.addCard(card);
			} else {
				cardEntry = cardEntryFactory.createCardEntry(card);
				cardEntry.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
				getChildren().add(cardEntry);
				existingCardEntries.put(cardId, cardEntry);
			}

		}
	}

	@Override
	public void handle(MouseEvent event) {
		Card card = null;
		for (CardEntry cardEntry : existingCardEntries.values()) {
			if (event.getSource() == cardEntry) {
				card = cardEntry.getCard();
				break;
			}

		}
		if (card != null) {
			NotificationProxy.sendNotification(GameNotification.REMOVE_CARD_FROM_DECK, card);
		}
	}

}
