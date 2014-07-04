package net.pferdimanzug.hearthstone.analyzer.playmode;

import javafx.scene.control.Tooltip;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

public class TooltipFactory {
	
	public static Tooltip createCardTooltip(GameContext context, Card card, Player player) {
		Tooltip tooltip = new Tooltip();
		CardTooltip content = new CardTooltip();
		content.setCard(context, card, player);
		tooltip.setGraphic(content);
		
		return tooltip;
	}
	
	private TooltipFactory() {}

}
