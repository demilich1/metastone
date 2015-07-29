package net.demilich.metastone.gui.deckbuilder;

import java.io.IOException;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import net.demilich.metastone.ApplicationFacade;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.gui.common.CardSetStringConverter;

public class CardFilterView extends HBox {

	@FXML
	private TextField searchField;

	@FXML
	private ComboBox<CardSet> cardSetBox;

	public CardFilterView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CardFilterView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		searchField.textProperty().addListener(this::textChanged);

		cardSetBox.setConverter(new CardSetStringConverter());
		cardSetBox.setItems(FXCollections.observableArrayList(CardSet.values()));
		cardSetBox.getSelectionModel().selectFirst();
		cardSetBox.valueProperty().addListener(this::setChanged);
	}

	private void filterChanged() {
		ApplicationFacade.getInstance().sendNotification(GameNotification.FILTER_CARDS,
				new CardFilter(searchField.getText(), cardSetBox.getSelectionModel().getSelectedItem()));
	}

	private void setChanged(ObservableValue<? extends CardSet> observable, CardSet oldValue, CardSet newValue) {
		filterChanged();
	}

	private void textChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		filterChanged();
	}

}
