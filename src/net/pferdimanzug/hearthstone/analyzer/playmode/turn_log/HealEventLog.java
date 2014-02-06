package net.pferdimanzug.hearthstone.analyzer.playmode.turn_log;

import net.pferdimanzug.hearthstone.analyzer.game.events.HealEvent;

@SuppressWarnings("serial")
public class HealEventLog extends GameLogEntry {
	
	public HealEventLog(HealEvent healEvent) {
		setText(healEvent.getTarget().getName() + " is healed for " + healEvent.getHealing());
	}

}
