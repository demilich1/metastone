package net.pferdimanzug.hearthstone.analyzer.playmode;

import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

public class TooltipFactory {
	
	private TooltipFactory() {}
	
	public static PopupWindow createCardTooltip(Card card, Player player) {
		Popup popup = new Popup();
		CardTooltip content = new CardTooltip();
		content.setCard(card, player);
		popup.setAutoFix(true);
		popup.setAutoHide(true);
		popup.getContent().add(content);
		return popup;
	}

}
