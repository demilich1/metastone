package net.pferdimanzug.hearthstone.analyzer.playmode;

import javafx.scene.control.Tooltip;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

public class TooltipFactory {
	
	public static Tooltip createCardTooltip(Card card, Player player) {
		Tooltip tooltip = new Tooltip();
		CardTooltip content = new CardTooltip();
		content.setCard(card, player);
		tooltip.setGraphic(content);
		
		return tooltip;
	}
	
	private TooltipFactory() {}

}
