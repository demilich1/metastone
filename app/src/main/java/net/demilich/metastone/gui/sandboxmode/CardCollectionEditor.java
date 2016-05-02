package net.demilich.metastone.gui.sandboxmode;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;

public class CardCollectionEditor extends SandboxEditor {

	private class CardStringConverter extends StringConverter<Card> {

		@Override
		public Card fromString(String arg0) {
			return null;
		}

		@Override
		public String toString(Card card) {
			String result = card.getName();
			result += " [" + card.getCardType() + "] ";
			result += "Mana: " + card.getBaseManaCost();
			return result;
		}

	}

	@FXML
	private Label cardCountLabel;
	@FXML
	private ListView<Card> editableListView;

	@FXML
	private ListView<Card> catalogueListView;
	@FXML
	private TextField filterTextfield;

	@FXML
	private Button clearFilterButton;
	@FXML
	private Button addCardButton;

	@FXML
	private Button removeCardButton;

	private ICardCollectionEditingListener listener;

	private int cardLimit;

	public CardCollectionEditor(String title, CardCollection cardCollection, ICardCollectionEditingListener listener, int cardLimit) {
		super("CardCollectionEditor.fxml");
		this.listener = listener;
		this.cardLimit = cardLimit;

		setTitle(title);

		editableListView.setCellFactory(TextFieldListCell.forListView(new CardStringConverter()));
		populateEditableView(cardCollection);

		catalogueListView.setCellFactory(TextFieldListCell.forListView(new CardStringConverter()));
		populateCatalogueView(null);

		filterTextfield.textProperty().addListener(this::onFilterTextChanged);
		clearFilterButton.setOnAction(actionEvent -> filterTextfield.clear());

		okButton.setOnAction(this::handleOkButton);
		cancelButton.setOnAction(this::handleCancelButton);

		addCardButton.setOnAction(this::handleAddCardButton);
		removeCardButton.setOnAction(this::handleRemoveCardButton);
	}

	private void handleAddCardButton(ActionEvent actionEvent) {
		for (Card selectedCard : catalogueListView.getSelectionModel().getSelectedItems()) {
			editableListView.getItems().add(selectedCard.clone());
		}
	}

	private void handleCancelButton(ActionEvent actionEvent) {
		this.getScene().getWindow().hide();
	}

	private void handleEditableCardListChanged(Change<? extends Card> change) {
		int count = editableListView.getItems().size();
		cardCountLabel.setText("Cards in collection: " + count + "/" + cardLimit);
		addCardButton.setDisable(count >= cardLimit);
	}

	private void handleOkButton(ActionEvent actionEvent) {
		CardCollection changedCollection = new CardCollection();
		for (Card card : editableListView.getItems()) {
			changedCollection.add(card);
		}
		listener.onFinishedEditing(changedCollection);
		this.getScene().getWindow().hide();
	}

	private void handleRemoveCardButton(ActionEvent actionEvent) {
		editableListView.getItems().remove(editableListView.getSelectionModel().getSelectedItem());
	}

	private void onFilterTextChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		populateCatalogueView(newValue);
	}

	private void populateCatalogueView(String filter) {
		ObservableList<Card> data = FXCollections.observableArrayList();
		for (Card card : CardCatalogue.getAll()) {
			if (card.getCardType().isCardType(CardType.HERO)) {
				continue;
			}
			if (filter == null || card.matchesFilter(filter)) {
				data.add(card);
			}
		}
		catalogueListView.setItems(data);
	}

	private void populateEditableView(CardCollection cardCollection) {
		ObservableList<Card> data = FXCollections.observableArrayList();
		for (Card card : cardCollection) {
			data.add(card);
		}
		editableListView.setItems(data);
		data.addListener(this::handleEditableCardListChanged);
		handleEditableCardListChanged(null);
	}

}
