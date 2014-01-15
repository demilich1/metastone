package net.pferdimanzug.hearthstone.analyzer.game.actions;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public abstract class GameAction {
	
	private TargetSelection targetRequirement = TargetSelection.NONE;
	private ActionType actionType = ActionType.UNDEFINED;
	private List<Entity> validTargets;
	private Entity source;
	private TargetKey targetKey;
	
	public boolean canBeExecutedOn(Entity entity) {
		return true;
	}
	
	public abstract void execute(GameContext context, int playerId);
	
	public ActionType getActionType() {
		return actionType;
	}

	public Entity getSource() {
		return source;
	}

	public TargetKey getTargetKey() {
		return targetKey;
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

	public void setTargetKey(TargetKey targetKey) {
		this.targetKey = targetKey;
	}
	
	public void setTarget(Entity target) {
		this.targetKey = TargetKey.pointTo(target);
	}

	protected void setTargetRequirement(TargetSelection targetRequirement) {
		this.targetRequirement = targetRequirement;
	}

	public void setValidTargets(List<Entity> validTargets) {
		this.validTargets = validTargets;
	}
}
