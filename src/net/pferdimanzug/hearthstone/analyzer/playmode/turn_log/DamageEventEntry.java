package net.pferdimanzug.hearthstone.analyzer.playmode.turn_log;

import net.pferdimanzug.hearthstone.analyzer.game.events.DamageEvent;

@SuppressWarnings("serial")
public class DamageEventEntry extends GameLogEntry {

	public DamageEventEntry(DamageEvent damageEvent) {
		setText(damageEvent.getVictim().getName() + " is damaged by " + damageEvent.getDamage());
	}

}
