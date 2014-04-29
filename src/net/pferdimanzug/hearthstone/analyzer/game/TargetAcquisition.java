package net.pferdimanzug.hearthstone.analyzer.game;

import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;

public abstract class TargetAcquisition {
	
	private final ActionType actionType;
	private Actor target;

	public TargetAcquisition(ActionType actionType, Actor target) {
		this.actionType = actionType;
		this.setTarget(target);
	}

	public Actor getTarget() {
		return target;
	}

	public void setTarget(Actor target) {
		this.target = target;
	}

	public ActionType getActionType() {
		return actionType;
	}

}
