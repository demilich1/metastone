package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;

public class HandCard extends BorderPane {
	@FXML
	private Label nameLabel;
	@FXML
	private Label manacostLabel;
	
	@FXML
	private Label attackLabel;
	@FXML
	private Label hpLabel;
	
	@FXML
	private ImageView attackIcon;
	@FXML
	private ImageView hpIcon;
	

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
		nameLabel.setText(card.getName());
		manacostLabel.setText(String.valueOf(card.getManaCost(player)));
		boolean isMinionCard = card.getCardType() == CardType.MINION;
		attackLabel.setVisible(isMinionCard);
		hpLabel.setVisible(isMinionCard);
		attackIcon.setVisible(isMinionCard);
		hpIcon.setVisible(isMinionCard);
		if (isMinionCard) {
			MinionCard minionCard = (MinionCard) card;
			attackLabel.setText(String.valueOf(minionCard.getBaseAttack()));
			hpLabel.setText(String.valueOf(minionCard.getBaseHp()));
		}
	}

}
