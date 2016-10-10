package net.demilich.metastone.gui.deckbuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.gui.common.CardSetStringConverter;
import net.demilich.metastone.gui.common.DeckFormatStringConverter;

public class CardFilterView extends HBox {

	@FXML
	private TextField searchField;

	@FXML
	private ComboBox<CardSet> cardSetBox;

	@FXML
	private ComboBox<DeckFormat> deckFormatBox;

	private List<DeckFormat> deckFormats = new ArrayList<DeckFormat>();

	public CardFilterView(List<DeckFormat> deckFormats) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CardFilterView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		searchField.textProperty().addListener(this::textChanged);

		deckFormatBox.setConverter(new DeckFormatStringConverter());
		DeckFormat deckFormat = new DeckFormat();
		deckFormat.setName("DECK FORMAT");
		deckFormats.add(0, deckFormat);
		deckFormatBox.setItems(FXCollections.observableArrayList(deckFormats));
		deckFormatBox.getSelectionModel().selectFirst();
		deckFormatBox.valueProperty().addListener(this::formatChanged);

		cardSetBox.setConverter(new CardSetStringConverter());
		cardSetBox.setItems(FXCollections.observableArrayList(CardSet.values()));
		cardSetBox.getSelectionModel().selectFirst();
		cardSetBox.valueProperty().addListener(this::setChanged);
	}

	private void filterChanged() {
		DeckFormat deckFormat = null;
		if (!deckFormatBox.getSelectionModel().isSelected(0)) {
			deckFormat = deckFormatBox.getSelectionModel().getSelectedItem();
		}
		NotificationProxy.sendNotification(GameNotification.FILTER_CARDS,
				new CardFilter(searchField.getText(), cardSetBox.getSelectionModel().getSelectedItem(), deckFormat));
	}

	private void formatChanged(ObservableValue<? extends DeckFormat> observable, DeckFormat oldValue, DeckFormat newValue) {
		CardSet set = cardSetBox.getSelectionModel().getSelectedItem();
		if (deckFormatBox.getSelectionModel().isSelected(0)) {
			cardSetBox.setItems(FXCollections.observableArrayList(CardSet.values()));
		} else {
			List<CardSet> sets = newValue.getCardSets();
			sets.add(0, CardSet.ANY);
			cardSetBox.setItems(FXCollections.observableArrayList(sets));
		}
		if (!deckFormatBox.getSelectionModel().isSelected(0) && !set.equals(CardSet.ANY) && !newValue.isInFormat(set)) {
			cardSetBox.getSelectionModel().selectFirst();
		} else {
			cardSetBox.getSelectionModel().select(set);
		}
		filterChanged();
	}

	public void injectDeckFormats(List<DeckFormat> deckFormats) {
		this.deckFormats.addAll(deckFormats);
	}

	private void setChanged(ObservableValue<? extends CardSet> observable, CardSet oldValue, CardSet newValue) {
		filterChanged();
	}

	private void textChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		filterChanged();
	}

}
