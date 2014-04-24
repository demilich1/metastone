package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TriggerLayer;

public abstract class GameEvent {

	private final GameContext context;
	private TriggerLayer triggerLayer;

	public GameEvent(GameContext context, TriggerLayer triggerLayer) {
		this.context = context;
		this.triggerLayer = triggerLayer;
	}

	public GameEvent(GameContext context) {
		this(context, TriggerLayer.DEFAULT);
	}

	public GameContext getGameContext() {
		return context;
	}

	public TriggerLayer getTriggerLayer() {
		return triggerLayer;
	}
	
	public GameEvent setTriggerLayer(TriggerLayer triggerLayer) {
		this.triggerLayer = triggerLayer;
		return this;
	}

	public abstract GameEventType getEventType();

}
