package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;

public abstract class GameEvent {

	private final GameContext context;
	private final int targetPlayerId;
	private final int sourcePlayerId;

	public GameEvent(GameContext context, int targetPlayerId, int sourcePlayerId) {
		this.context = context;
		this.targetPlayerId = targetPlayerId;
		this.sourcePlayerId = sourcePlayerId;
	}

	/**
	 * Spells may specify to be cast on the event target; this is dependent on
	 * the actual event. For example, a SummonEvent may return the summoned
	 * minion, a DamageEvent may return the damaged minion/hero, etc.
	 * 
	 * @return
	 */
	public abstract Entity getEventTarget();
	
	public Entity getEventSource() {
		return null;
	}
	
	public abstract GameEventType getEventType();

	public GameContext getGameContext() {
		return context;
	}

	public int getTargetPlayerId() {
		return targetPlayerId;
	}
	
	public int getSourcePlayerId() {
		return sourcePlayerId;
	}

	@Override
	public String toString() {
		return "[EVENT " + getClass().getSimpleName() + "]";
	}
}
