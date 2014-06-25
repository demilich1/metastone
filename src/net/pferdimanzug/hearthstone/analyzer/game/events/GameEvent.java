package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TriggerLayer;

public abstract class GameEvent {

	private final GameContext context;
	private TriggerLayer triggerLayer = TriggerLayer.DEFAULT;

	public GameEvent(GameContext context) {
		this.context = context;
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

}
