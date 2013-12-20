package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;

public class Battlecry extends GameAction {
	
	private final ISpell spell;
	
	public static Battlecry createBattlecry(ISpell spell, TargetRequirement targetSelection) {
		Battlecry battlecry = new Battlecry(spell);
		battlecry.setTargetRequirement(targetSelection);
		return battlecry;
	}
	
	public static Battlecry createBattlecry(ISpell spell) {
		return createBattlecry(spell, TargetRequirement.NONE);
	}

	protected Battlecry(ISpell spell) {
		this.spell = spell;
		setActionType(ActionType.MINION_ABILITY);
	}

	@Override
	public void execute(GameContext context, Player player) {
		context.getLogic().castSpell(player, spell, getTarget());
		
	}
}
