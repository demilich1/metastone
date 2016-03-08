package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.entities.Entity;

public class TargetAcquisitionEvent extends GameEvent {

	private final Entity target;
	private final Entity source;
	private final ActionType actionType;

	public TargetAcquisitionEvent(GameContext context, int playerId, ActionType actionType, Entity source, Entity target) {
		super(context, playerId, source.getOwner());
		this.actionType = actionType;
		this.source = source;
		this.target = target;
	}

	public ActionType getActionType() {
		return actionType;
	}
	
	@Override
	public Entity getEventSource() {
		return getSource();
	}

	@Override
	public Entity getEventTarget() {
		return getTarget();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.TARGET_ACQUISITION;
	}

	public Entity getSource() {
		return source;
	}

	public Entity getTarget() {
		return target;
	}

}
