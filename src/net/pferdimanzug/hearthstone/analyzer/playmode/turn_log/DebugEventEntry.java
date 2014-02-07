package net.pferdimanzug.hearthstone.analyzer.playmode.turn_log;

import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;

@SuppressWarnings("serial")
public class DebugEventEntry extends GameLogEntry {
	
	public DebugEventEntry(GameEvent gameEvent) {
		setText("EVENT: " + gameEvent);
	}

}
