package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public abstract class GameAction {
	
	private TargetRequirement targetRequirement = TargetRequirement.NONE;
	private ActionType actionType = ActionType.UNDEFINED;
	private EffectHint effectHint = EffectHint.UNKNOWN;
	
	private Entity target;
	
	public abstract void execute(GameContext context, Player player);
	
	public TargetRequirement getTargetRequirement() {
		return targetRequirement;
	}
	
	public EffectHint getEffectHint() {
		return effectHint;
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

	public ActionType getActionType() {
		return actionType;
	}

	protected void setTargetRequirement(TargetRequirement targetRequirement) {
		this.targetRequirement = targetRequirement;
	}

	protected void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	protected void setEffectHint(EffectHint effectHint) {
		this.effectHint = effectHint;
	}
	
	public boolean canBeExecutedOn(Entity entity) {
		return true;
	}
}
