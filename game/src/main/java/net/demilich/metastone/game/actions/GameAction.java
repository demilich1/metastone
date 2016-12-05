package net.demilich.metastone.game.actions;

import java.io.Serializable;

import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;
import com.google.gson.annotations.SerializedName;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class GameAction implements Cloneable, Serializable {

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

	@Suspendable
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
	public boolean equals(Object other) {
		if (other instanceof GameAction) {
			GameAction otherAction = (GameAction) other;
			return (this.actionType == otherAction.actionType)
					&& (this.targetRequirement == otherAction.targetRequirement)
					&& (this.getSource().equals(otherAction.getSource()))
					&& (this.getTargetKey().equals(otherAction.getTargetKey()));
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(targetRequirement)
				.append(actionType)
				.append(source)
				.append(targetKey)
				.append(actionSuffix)
				.toHashCode();
	}
}
