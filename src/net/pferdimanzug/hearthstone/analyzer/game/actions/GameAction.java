package net.pferdimanzug.hearthstone.analyzer.game.actions;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public abstract class GameAction {
	
	private TargetSelection targetRequirement = TargetSelection.NONE;
	private ActionType actionType = ActionType.UNDEFINED;
	private List<Entity> validTargets;
	private EntityReference source;
	private EntityReference targetKey;
	
	public boolean canBeExecutedOn(Entity entity) {
		return true;
	}
	
	public abstract void execute(GameContext context, int playerId);
	
	public ActionType getActionType() {
		return actionType;
	}

	public EntityReference getSource() {
		return source;
	}

	public EntityReference getTargetKey() {
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

	public void setSource(EntityReference source) {
		this.source = source;
	}

	public void setTarget(Entity target) {
		this.targetKey = EntityReference.pointTo(target);
	}
	
	public void setTargetKey(EntityReference targetKey) {
		this.targetKey = targetKey;
	}

	protected void setTargetRequirement(TargetSelection targetRequirement) {
		this.targetRequirement = targetRequirement;
	}

	public void setValidTargets(List<Entity> validTargets) {
		this.validTargets = validTargets;
	}
}
