package net.demilich.metastone.gui.deckbuilder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.decks.MetaDeck;
import net.demilich.metastone.gui.deckbuilder.metadeck.MetaDeckListView;
import net.demilich.metastone.gui.deckbuilder.metadeck.MetaDeckView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeckBuilderView extends BorderPane implements EventHandler<ActionEvent> {

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private Pane lowerInfoArea;

	@FXML
	private Pane upperInfoArea;

	@FXML
	private TextField importField;

	@FXML
	private Button importButton;

	@FXML
	private Button backButton;

	private final CardView cardView;
	private final CardListView cardListView;
	private final DeckInfoView deckInfoView;
	private final DeckListView deckListView;
	private final DeckNameView deckNameView;
	private final MetaDeckView metaDeckView;
	private final MetaDeckListView metaDeckListView;

	private List<DeckFormat> deckFormats = new ArrayList<DeckFormat>();

	public DeckBuilderView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DeckBuilderView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		importButton.setOnAction(this);
		backButton.setOnAction(this);

		cardView = new CardView();
		cardListView = new CardListView();
		deckInfoView = new DeckInfoView();
		deckListView = new DeckListView();
		deckNameView = new DeckNameView();

		metaDeckView = new MetaDeckView();
		metaDeckListView = new MetaDeckListView();
		showSidebar(deckListView);
	}

	public void activeDeckChanged(Deck activeDeck) {
		if (activeDeck.isMetaDeck()) {
			MetaDeck metaDeck = (MetaDeck) activeDeck;
			metaDeckListView.displayDecks(metaDeck.getDecks());
			metaDeckView.deckChanged(metaDeck);
		} else {
			activeDeck.getCards().sortByManaCost();
			cardListView.displayDeck(activeDeck);

		}
		deckInfoView.updateDeck(activeDeck);
		deckNameView.updateDeck(activeDeck);

	}

	public void createNewDeck() {
		showMainArea(new ChooseClassView());
		showSidebar(null);
	}

	public void displayDecks(List<Deck> decks) {
		deckListView.displayDecks(decks);
		metaDeckView.displayDecks(decks);
	}

	public void editDeck(Deck deck) {
		if (deck.isMetaDeck()) {
			showMainArea(metaDeckView);
			showSidebar(metaDeckListView);
		} else {
			showMainArea(cardView);
			showSidebar(cardListView);
			showBottomBar(new CardFilterView(deckFormats));
		}
		showLowerInfoArea(deckInfoView);
		showUpperInfoArea(deckNameView);

	}

	public void filteredCards(List<Card> filteredCards) {
		cardView.displayCards(filteredCards);
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == importButton) {
			NotificationProxy.sendNotification(GameNotification.IMPORT_DECK_FROM_URL, importField.getText());
		} else if (event.getSource() == backButton) {
			NotificationProxy.sendNotification(GameNotification.MAIN_MENU);
		}
	}

	public void injectDeckFormats(List<DeckFormat> deckFormats) {
		this.deckFormats.addAll(deckFormats);
	}

	private void showBottomBar(Node content) {
		BorderPane.setAlignment(content, Pos.CENTER);
		setBottom(content);
	}

	private void showLowerInfoArea(Node content) {
		lowerInfoArea.getChildren().clear();
		lowerInfoArea.getChildren().add(content);
	}

	private void showMainArea(Node content) {
		setCenter(content);
	}

	private void showSidebar(Node content) {
		scrollPane.setVisible(content != null);
		scrollPane.setContent(content);
	}

	private void showUpperInfoArea(Node content) {
		upperInfoArea.getChildren().clear();
		upperInfoArea.getChildren().add(content);
	}

}
