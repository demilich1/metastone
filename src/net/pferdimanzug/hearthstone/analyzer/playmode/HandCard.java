package net.pferdimanzug.hearthstone.analyzer.playmode;

import javafx.scene.control.Tooltip;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

public class HandCard extends CardToken {

	public HandCard() {
		super("HandCard.fxml");
	}

	@Override
	public void setCard(Card card, Player player) {
		super.setCard(card, player);
		Tooltip.install(this, TooltipFactory.createCardTooltip(card, player));
	}

}
