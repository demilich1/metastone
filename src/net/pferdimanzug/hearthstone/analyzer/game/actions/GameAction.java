package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public abstract class GameAction {
	
	private TargetSelection targetRequirement = TargetSelection.NONE;
	private ActionType actionType = ActionType.UNDEFINED;
	private EntityReference source;
	private EntityReference targetKey;
	private String actionSuffix;
	
	public boolean canBeExecutedOn(Entity entity) {
		return true;
	}
	
	public abstract void execute(GameContext context, int playerId);
	
	public String getActionSuffix() {
		return actionSuffix;
	}

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

	public void setActionSuffix(String actionSuffix) {
		this.actionSuffix = actionSuffix;
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
}
