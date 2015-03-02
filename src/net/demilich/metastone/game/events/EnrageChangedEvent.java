package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;

public class EnrageChangedEvent extends GameEvent {
	
	private final Entity target;

	public EnrageChangedEvent(GameContext context, Entity target) {
		super(context);
		this.target = target;
	}

	@Override
	public Entity getEventTarget() {
		return target;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.ENRAGE_CHANGED;
	}

}
