package net.demilich.metastone.game.spells.trigger;

import java.io.Serializable;

import co.paralleluniverse.fibers.Suspendable;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.targeting.EntityReference;

public interface IGameEventListener extends Serializable{

	public IGameEventListener clone();
	
	public abstract boolean canFire(GameEvent event);

	public abstract EntityReference getHostReference();

	public abstract int getOwner();

	public abstract boolean interestedIn(GameEventType eventType);

	public abstract boolean isExpired();

	public abstract void onAdd(GameContext context);

	@Suspendable
	public abstract void onGameEvent(GameEvent event);

	public abstract void onRemove(GameContext context);

	public abstract void setHost(Entity host);

	public abstract void setOwner(int playerIndex);
	
	public abstract boolean hasPersistentOwner();

	public abstract boolean oneTurnOnly();

	public abstract boolean isDelayed();

	public abstract void delayTimeDown();

	public abstract void expire();
	
	public abstract boolean canFireCondition(GameEvent event);

}