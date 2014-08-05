package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public interface IGameEventListener {

	public IGameEventListener clone();

	public abstract EntityReference getHostReference();

	public abstract TriggerLayer getLayer();

	public abstract int getOwner();

	public abstract boolean interestedIn(GameEventType eventType);

	public abstract boolean isExpired();

	public abstract void onAdd(GameContext context);

	public abstract void onGameEvent(GameEvent event);

	public abstract void onRemove(GameContext context);

	public abstract void reset();

	public abstract void setHost(Entity host);
	
	public abstract void setOwner(int playerIndex);

}