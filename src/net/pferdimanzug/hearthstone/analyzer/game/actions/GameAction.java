package net.pferdimanzug.hearthstone.analyzer.game.actions;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public abstract class GameAction {
	
	private TargetSelection targetRequirement = TargetSelection.NONE;
	private ActionType actionType = ActionType.UNDEFINED;
	private List<Entity> validTargets;
	private Entity source;
	private Entity target;
	
	public abstract void execute(GameContext context, Player player);
	
	public TargetSelection getTargetRequirement() {
		return targetRequirement;
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

	protected void setTargetRequirement(TargetSelection targetRequirement) {
		this.targetRequirement = targetRequirement;
	}

	protected void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public boolean canBeExecutedOn(Entity entity) {
		return true;
	}

	public List<Entity> getValidTargets() {
		return validTargets;
	}

	public void setValidTargets(List<Entity> validTargets) {
		this.validTargets = validTargets;
	}

	public Entity getSource() {
		return source;
	}

	public void setSource(Entity source) {
		this.source = source;
	}
}
