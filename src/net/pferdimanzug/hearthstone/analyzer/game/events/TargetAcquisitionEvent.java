package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;

public class TargetAcquisitionEvent extends GameEvent {

	private final Actor target;
	private final ActionType actionType;

	public TargetAcquisitionEvent(GameContext context, ActionType actionType,  Actor target) {
		super(context);
		this.actionType = actionType;
		this.target = target;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.TARGET_ACQUISITION;
	}

	public Actor getTarget() {
		return target;
	}

	public ActionType getActionType() {
		return actionType;
	}

}
