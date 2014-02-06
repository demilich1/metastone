package net.pferdimanzug.hearthstone.analyzer.playmode.turn_log;

import net.pferdimanzug.hearthstone.analyzer.game.Player;

public class TurnEndActionEntry extends GameLogEntry {
	private static final String ICON_PATH = "";

	public TurnEndActionEntry(Player player) {
		
		setText(player.getName() + " ends his turn.");
	}
}
