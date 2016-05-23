package net.demilich.metastone.gui.deckbuilder;

import java.io.IOException;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.MetaDeck;
import net.demilich.metastone.game.entities.heroes.HeroClass;

public class ChooseClassView extends BorderPane implements EventHandler<ActionEvent> {
	@FXML
	private Button warriorButton;

	@FXML
	private Button paladinButton;

	@FXML
	private Button druidButton;

	@FXML
	private Button rogueButton;

	@FXML
	private Button warlockButton;

	@FXML
	private Button hunterButton;

	@FXML
	private Button shamanButton;

	@FXML
	private Button mageButton;

	@FXML
	private Button priestButton;

	@FXML
	private Button collectionButton;
	
	@FXML
	private CheckBox arbitraryCheckBox;
	
	private boolean arbitrary;

	public ChooseClassView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ChooseClassView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		arbitrary = false;
		
		setupArbitraryBox();

		warriorButton.setOnAction(this);
		paladinButton.setOnAction(this);
		druidButton.setOnAction(this);

		rogueButton.setOnAction(this);
		warlockButton.setOnAction(this);
		hunterButton.setOnAction(this);

		shamanButton.setOnAction(this);
		mageButton.setOnAction(this);
		priestButton.setOnAction(this);

		collectionButton.setOnAction(this);
	}

	@Override
	public void handle(ActionEvent event) {
		Deck newDeck = null;
		if (event.getSource() == warriorButton) {
			newDeck = new Deck(HeroClass.WARRIOR, arbitrary);
		} else if (event.getSource() == paladinButton) {
			newDeck = new Deck(HeroClass.PALADIN, arbitrary);
		} else if (event.getSource() == druidButton) {
			newDeck = new Deck(HeroClass.DRUID, arbitrary);
		} else if (event.getSource() == rogueButton) {
			newDeck = new Deck(HeroClass.ROGUE, arbitrary);
		} else if (event.getSource() == warlockButton) {
			newDeck = new Deck(HeroClass.WARLOCK, arbitrary);
		} else if (event.getSource() == hunterButton) {
			newDeck = new Deck(HeroClass.HUNTER, arbitrary);
		} else if (event.getSource() == shamanButton) {
			newDeck = new Deck(HeroClass.SHAMAN, arbitrary);
		} else if (event.getSource() == mageButton) {
			newDeck = new Deck(HeroClass.MAGE, arbitrary);
		} else if (event.getSource() == priestButton) {
			newDeck = new Deck(HeroClass.PRIEST, arbitrary);
		} else if (event.getSource() == collectionButton) {
			newDeck = new MetaDeck();
		}
		NotificationProxy.sendNotification(GameNotification.SET_ACTIVE_DECK, newDeck);
	}

	private void onArbitraryBoxChanged(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
		arbitrary = newValue;
//		deckProxy = (DeckProxy) getFacade().retrieveProxy(DeckProxy.NAME);
//		deckProxy.setActiveDeckValidator(new ArbitraryDeckValidator());
	}

	private void setupArbitraryBox() {
		arbitraryCheckBox.selectedProperty().addListener(this::onArbitraryBoxChanged);
	}
}
