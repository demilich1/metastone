package net.demilich.metastone.game.actions;

import java.util.function.Predicate;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class BattlecryAction extends GameAction {

	public static BattlecryAction createBattlecry(SpellDesc spell) {
		return createBattlecry(spell, TargetSelection.NONE);
	}

	public static BattlecryAction createBattlecry(SpellDesc spell, TargetSelection targetSelection) {
		BattlecryAction battlecry = new BattlecryAction(spell);
		battlecry.setTargetRequirement(targetSelection);
		return battlecry;
	}

	private final SpellDesc spell;
	private boolean resolvedLate = false;
	private IBattlecryCondition condition;
	private Predicate<Entity> entityFilter;

	protected BattlecryAction(SpellDesc spell) {
		this.spell = spell;
		setActionType(ActionType.BATTLECRY);
	}

	public boolean canBeExecuted(GameContext context, Player player) {
		if (condition == null) {
			return true;
		}
		return condition.isFulfilled(context, player);
	}

	@Override
	public final boolean canBeExecutedOn(GameContext context, Entity entity) {
		if (!super.canBeExecutedOn(context, entity)) {
			return false;
		}
		if (getSource().getId() == entity.getId()) {
			return false;
		}
		if (getEntityFilter() == null) {
			return true;
		}
		return getEntityFilter().test(entity);
	}

	@Override
	public BattlecryAction clone() {
		BattlecryAction clone = BattlecryAction.createBattlecry(getSpell(), getTargetRequirement());
		clone.setActionSuffix(getActionSuffix());
		clone.setCondition(getCondition());
		clone.setEntityFilter(getEntityFilter());
		clone.setResolvedLate(isResolvedLate());
		clone.setSource(getSource());
		return clone;
	}

	@Override
	public void execute(GameContext context, int playerId) {
		EntityReference target = getSpell().hasPredefinedTarget() ? getSpell().getTarget() : getTargetKey();
		context.getLogic().castSpell(playerId, getSpell(), getSource(), target);
	}

	public IBattlecryCondition getCondition() {
		return condition;
	}

	public Predicate<Entity> getEntityFilter() {
		return entityFilter;
	}

	@Override
	public String getPromptText() {
		return "[Battlecry]";
	}

	public SpellDesc getSpell() {
		return spell;
	}

	public boolean isResolvedLate() {
		return resolvedLate;
	}

	@Override
	public boolean isSameActionGroup(GameAction anotherAction) {
		return anotherAction.getActionType() == getActionType();
	}

	public void setCondition(IBattlecryCondition condition) {
		this.condition = condition;
	}

	public void setEntityFilter(Predicate<Entity> entityFilter) {
		this.entityFilter = entityFilter;
	}

	public void setResolvedLate(boolean resolvedLate) {
		this.resolvedLate = resolvedLate;
	}

	@Override
	public String toString() {
		return String.format("[%s '%s' resolvedLate:%s]", getActionType(), getSpell().getClass().getSimpleName(), resolvedLate);
	}
}
