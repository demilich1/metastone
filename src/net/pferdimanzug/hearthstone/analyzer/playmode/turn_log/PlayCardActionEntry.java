package net.pferdimanzug.hearthstone.analyzer.playmode.turn_log;

import javax.swing.ImageIcon;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;

public class PlayCardActionEntry extends GameLogEntry {
	
	public PlayCardActionEntry(Player player, PlayCardAction playCardAction) {
		ImageIcon icon = new ImageIcon(
				BattlecryActionEntry.class.getResource("/net/pferdimanzug/hearthstone/analyzer/resources/img/common/icon_play_card_action.png"));
		setIcon(icon);
		setText(player.getName() + " plays " + playCardAction.getCard().getName());
	}

}
