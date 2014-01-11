package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;

public class Battlecry extends GameAction {
	
	public static Battlecry createBattlecry(ISpell spell) {
		return createBattlecry(spell, TargetSelection.NONE);
	}
	
	public static Battlecry createBattlecry(ISpell spell, TargetSelection targetSelection) {
		Battlecry battlecry = new Battlecry(spell);
		battlecry.setTargetRequirement(targetSelection);
		return battlecry;
	}
	
	private final ISpell spell;

	protected Battlecry(ISpell spell) {
		this.spell = spell;
		setActionType(ActionType.MINION_ABILITY);
	}

	@Override
	public void execute(GameContext context, Player player) {
		context.getLogic().castSpell(player, spell, getTarget());
		
	}
}
