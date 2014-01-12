package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
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

	protected Battlecry(Spell spell) {
		this.spell = spell;
		setActionType(ActionType.MINION_ABILITY);
	}

	@Override
	public void execute(GameContext context, Player player) {
		spell.setTarget(getTargetKey());
		context.getLogic().castSpell(player, spell);
	}
}
