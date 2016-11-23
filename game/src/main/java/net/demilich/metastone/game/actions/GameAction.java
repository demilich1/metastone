package net.demilich.metastone.game.actions;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public abstract class GameAction implements Cloneable {

	private TargetSelection targetRequirement = TargetSelection.NONE;
	private ActionType actionType = ActionType.SYSTEM;
	private EntityReference source;
	private EntityReference targetKey;
	private String actionSuffix;

	public boolean canBeExecutedOn(GameContext gameContext, Player player, Entity entity) {
		return true;
	}

	@Override
	public GameAction clone() {
		try {
			return (GameAction) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public abstract void execute(GameContext context, int playerId);

	public String getActionSuffix() {
		return actionSuffix;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public abstract String getPromptText();

	public EntityReference getSource() {
		return source;
	}

	public EntityReference getTargetKey() {
		return targetKey;
	}

	public TargetSelection getTargetRequirement() {
		return targetRequirement;
	}

	public abstract boolean isSameActionGroup(GameAction anotherAction);

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

	public void setTargetRequirement(TargetSelection targetRequirement) {
		this.targetRequirement = targetRequirement;
	}

	@Override
	public String toString() {
		return "Action " + actionType.toString() + " " + actionSuffix + " from " + source.toString();
	}
}
