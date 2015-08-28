package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;

public class JoustEvent extends GameEvent {

	private final int playerId;
	private final boolean won;

	public JoustEvent(GameContext context, int playerId, boolean won) {
		super(context);
		this.playerId = playerId;
		this.won = won;
	}

	@Override
	public Entity getEventTarget() {
		return null;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.JOUST;
	}

	public int getPlayerId() {
		return playerId;
	}

	public boolean isWon() {
		return won;
	}

}
