package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;

public class TurnEndEvent extends GameEvent {

	private final Player player;

	public TurnEndEvent(GameContext context, Player player) {
		super(context);
		this.player = player;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.TURN_END;
	}

	public Player getPlayer() {
		return player;
	}

}
