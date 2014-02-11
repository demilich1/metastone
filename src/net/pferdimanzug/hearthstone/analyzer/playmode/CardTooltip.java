package net.pferdimanzug.hearthstone.analyzer.playmode;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;


public class CardTooltip extends CardToken {

	public CardTooltip() {
		super("CardTooltip.fxml");
	}

	@Override
	public void setCard(Card card, Player player) {
		super.setCard(card, player);
		descriptionLabel.setText(card.getDescription());
	}
	
	

}
