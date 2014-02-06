package net.pferdimanzug.hearthstone.analyzer.playmode.turn_log;

import javax.swing.ImageIcon;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;

@SuppressWarnings("serial")
public class BattlecryActionEntry extends GameLogEntry {

	private static final String ICON_PATH = "";

	public BattlecryActionEntry(Battlecry battlecry) {
		ImageIcon icon = new ImageIcon(
				BattlecryActionEntry.class.getResource("/net/pferdimanzug/hearthstone/analyzer/resources/img/common/icon_battlecry_action.png"));
		setIcon(icon);
		setText("Battlecry!");
	}
}
