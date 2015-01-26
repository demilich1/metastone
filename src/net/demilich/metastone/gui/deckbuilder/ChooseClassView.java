package net.demilich.metastone.gui.deckbuilder;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import net.demilich.metastone.ApplicationFacade;
import net.demilich.metastone.GameNotification;
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

	public ChooseClassView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChooseClassView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

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
			newDeck = new Deck(HeroClass.WARRIOR);
		} else if (event.getSource() == paladinButton) {
			newDeck = new Deck(HeroClass.PALADIN);
		} else if (event.getSource() == druidButton) {
			newDeck = new Deck(HeroClass.DRUID);
		} else if (event.getSource() == rogueButton) {
			newDeck = new Deck(HeroClass.ROGUE);
		} else if (event.getSource() == warlockButton) {
			newDeck = new Deck(HeroClass.WARLOCK);
		} else if (event.getSource() == hunterButton) {
			newDeck = new Deck(HeroClass.HUNTER);
		} else if (event.getSource() == shamanButton) {
			newDeck = new Deck(HeroClass.SHAMAN);
		} else if (event.getSource() == mageButton) {
			newDeck = new Deck(HeroClass.MAGE);
		} else if (event.getSource() == priestButton) {
			newDeck = new Deck(HeroClass.PRIEST);
		} else if (event.getSource() == collectionButton) {
			newDeck = new MetaDeck();
		}
		ApplicationFacade.getInstance().sendNotification(GameNotification.SET_ACTIVE_DECK, newDeck);
	}
}
