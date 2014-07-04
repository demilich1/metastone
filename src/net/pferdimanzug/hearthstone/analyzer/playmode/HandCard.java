package net.pferdimanzug.hearthstone.analyzer.playmode;

import javafx.scene.control.Tooltip;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

public class HandCard extends CardToken {

	public HandCard() {
		super("HandCard.fxml");
	}

	@Override
	public void setCard(GameContext context, Card card, Player player) {
		super.setCard(context, card, player);
		Tooltip.install(this, TooltipFactory.createCardTooltip(context, card, player));
	}

}
