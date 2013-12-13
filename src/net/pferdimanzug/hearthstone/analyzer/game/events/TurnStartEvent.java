package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;

public class TurnStartEvent extends GameEvent {

	private final Player player;

	public TurnStartEvent(GameContext context, Player player) {
		super(context);
		this.player = player;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.TURN_START;
	}

	public Player getPlayer() {
		return player;
	}

}
