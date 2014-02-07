package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

public class HandCard extends BorderPane {
	@FXML
	private Label name;
	@FXML
	private Label manacost;

	public HandCard() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HandCard.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public void setCard(Card card, Player player) {
		name.setText(card.getName());
		manacost.setText(String.valueOf(card.getManaCost(player)));
	}

}
