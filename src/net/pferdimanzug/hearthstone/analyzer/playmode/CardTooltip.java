package net.pferdimanzug.hearthstone.analyzer.playmode;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;


public class CardTooltip extends CardToken {
	
	@FXML
	private Label raceLabel;

	public CardTooltip() {
		super("CardTooltip.fxml");
	}

	@Override
	public void setCard(Card card, Player player) {
		super.setCard(card, player);
		descriptionLabel.setText(card.getDescription());
		if (!card.hasTag(GameTag.RACE) || card.getTag(GameTag.RACE) == Race.NONE) {
			raceLabel.setVisible(false);
		} else {
			raceLabel.setText(card.getTag(GameTag.RACE).toString());
			raceLabel.setVisible(true);
		}
	}
	
	

}
