package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.trigger.TriggerLayer;

public abstract class GameEvent {

	private final GameContext context;
	private TriggerLayer triggerLayer = TriggerLayer.DEFAULT;
	private final int playerId;

	public GameEvent(GameContext context, int playerId) {
		this.context = context;
		this.playerId = playerId;
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

	public TriggerLayer getTriggerLayer() {
		return triggerLayer;
	}

	public void setTriggerLayer(TriggerLayer triggerLayer) {
		this.triggerLayer = triggerLayer;
	}

	public int getPlayerId() {
		return playerId;
	}

}
