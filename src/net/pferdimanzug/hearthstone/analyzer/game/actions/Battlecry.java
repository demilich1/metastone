package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
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

	protected Battlecry(Spell spell) {
		this.spell = spell;
		setActionType(ActionType.MINION_ABILITY);
	}

	@Override
	public boolean canBeExecutedOn(Actor entity) {
		if (!super.canBeExecutedOn(entity)) {
			return false;
		}
		if (getTargetRequirement() != TargetSelection.SELF && getSource().getId() == entity.getId()) {
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

	public boolean isResolvedLate() {
		return resolvedLate;
	}

	public void setResolvedLate(boolean resolvedLate) {
		this.resolvedLate = resolvedLate;
	}
}
