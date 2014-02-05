package net.pferdimanzug.hearthstone.analyzer.playmode.turn_log;

import javax.swing.JPanel;

import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;

@SuppressWarnings("serial")
public abstract class GameEventEntry extends JPanel {

	private final GameEvent event;

	public GameEventEntry(GameEvent event) {
		this.event = event;
	}

}
