package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.io.IOException;

import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DeckInfoView extends HBox {
	
	@FXML
	private Button doneButton;
	
	@FXML
	private Label cardCountLabel;
	
	public DeckInfoView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeckInfoView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		doneButton.setDisable(true);
		doneButton.setOnAction(actionEvent -> ApplicationFacade.getInstance().sendNotification(GameNotification.SAVE_DECK));
	}
	
	public void updateDeck(Deck deck) {
		doneButton.setDisable(deck.getCards().getCount() < GameLogic.DECK_SIZE);
		cardCountLabel.setText(deck.getCards().getCount() + "/" + GameLogic.DECK_SIZE);
	}

}
