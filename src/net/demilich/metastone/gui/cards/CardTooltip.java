package net.demilich.metastone.gui.cards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.minions.Race;


public class CardTooltip extends CardToken {
	
	@FXML
	private Label raceLabel;

	public CardTooltip() {
		super("CardTooltip.fxml");
	}

	@Override
	public void setCard(GameContext context, Card card, Player player) {
		super.setCard(context, card, player);
		descriptionLabel.setText(card.getDescription());
		if (!card.hasTag(GameTag.RACE) || card.getTag(GameTag.RACE) == Race.NONE) {
			raceLabel.setVisible(false);
		} else {
			raceLabel.setText(card.getTag(GameTag.RACE).toString());
			raceLabel.setVisible(true);
		}
	}
	
	

}
