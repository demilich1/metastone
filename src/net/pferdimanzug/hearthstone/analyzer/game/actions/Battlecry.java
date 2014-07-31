package net.pferdimanzug.hearthstone.analyzer.game.actions;

import java.util.function.Predicate;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellSource;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Battlecry extends GameAction {
	
	public static Battlecry createBattlecry(Spell spell) {
		return createBattlecry(spell, TargetSelection.NONE);
	}
	
	public static Battlecry createBattlecry(Spell spell, TargetSelection targetSelection) {
		Battlecry battlecry = new Battlecry(spell);
		battlecry.setTargetRequirement(targetSelection);
		return battlecry;
	}
	
	private final Spell spell;
	private boolean resolvedLate = false;
	private IBattlecryCondition condition;
	private Predicate<Entity> entityFilter;

	protected Battlecry(Spell spell) {
		this.spell = spell;
		spell.setSource(SpellSource.SPELL_TRIGGER);
		setActionType(ActionType.BATTLECRY);
	}

	public boolean canBeExecuted(GameContext context, Player player) {
		if (condition == null) {
			return true;
		}
		return condition.isFulfilled(context, player);
	}
	
	@Override
	public final boolean canBeExecutedOn(Entity entity) {
		if (!super.canBeExecutedOn(entity)) {
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
	public void execute(GameContext context, int playerId) {
		if (!spell.hasPredefinedTarget()) {
			spell.setTarget(getTargetKey());
		}
		
		spell.setSourceEntity(getSource());
		context.getLogic().castSpell(playerId, spell);
	}

	public IBattlecryCondition getCondition() {
		return condition;
	}

	public Predicate<Entity> getEntityFilter() {
		return entityFilter;
	}

	public boolean isResolvedLate() {
		return resolvedLate;
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
		return String.format("[%s '%s' resolvedLate:%s]", getActionType(), spell.getClass().getSimpleName(), resolvedLate);
	}
}
