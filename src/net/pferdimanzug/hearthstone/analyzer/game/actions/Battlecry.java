package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
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

	protected Battlecry(Spell spell) {
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
	public boolean canBeExecutedOn(Entity entity) {
		if (!super.canBeExecutedOn(entity)) {
			return false;
		}
		if (getSource().getId() == entity.getId()) {
			return false;
		}
		return true;
	}


	@Override
	public void execute(GameContext context, int playerId) {
		if (!spell.hasPredefinedTarget()) {
			spell.setTarget(getTargetKey());
		}
		
		spell.setSource(getSource());
		context.getLogic().castSpell(playerId, spell);
	}

	public IBattlecryCondition getCondition() {
		return condition;
	}

	public boolean isResolvedLate() {
		return resolvedLate;
	}

	public void setCondition(IBattlecryCondition condition) {
		this.condition = condition;
	}

	public void setResolvedLate(boolean resolvedLate) {
		this.resolvedLate = resolvedLate;
	}
}
