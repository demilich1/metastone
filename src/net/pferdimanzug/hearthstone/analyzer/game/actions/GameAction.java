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
	
	public boolean canBeExecutedOn(Entity entity) {
		return true;
	}
	
	public abstract void execute(GameContext context, Player player);
	
	public ActionType getActionType() {
		return actionType;
	}

	public Entity getSource() {
		return source;
	}

	public Entity getTarget() {
		return target;
	}

	public TargetSelection getTargetRequirement() {
		return targetRequirement;
	}

	public List<Entity> getValidTargets() {
		return validTargets;
	}

	protected void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public void setSource(Entity source) {
		this.source = source;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

	protected void setTargetRequirement(TargetSelection targetRequirement) {
		this.targetRequirement = targetRequirement;
	}

	public void setValidTargets(List<Entity> validTargets) {
		this.validTargets = validTargets;
	}
}
