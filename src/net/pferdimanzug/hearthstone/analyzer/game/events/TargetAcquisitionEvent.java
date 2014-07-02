package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class TargetAcquisitionEvent extends GameEvent {

	private final Entity target;
	private final ActionType actionType;

	public TargetAcquisitionEvent(GameContext context, ActionType actionType,  Entity target) {
		super(context);
		this.actionType = actionType;
		this.target = target;
	}

	public ActionType getActionType() {
		return actionType;
	}

	@Override
	public Entity getEventTarget() {
		return getTarget();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.TARGET_ACQUISITION;
	}

	public Entity getTarget() {
		return target;
	}

}
