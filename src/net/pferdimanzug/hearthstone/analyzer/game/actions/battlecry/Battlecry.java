package net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;

public class Battlecry extends GameAction {
	
	private final ISpell spell;
	
	public static Battlecry createBattlecry(ISpell spell, TargetRequirement targetSelection) {
		Battlecry battlecry = new Battlecry(spell);
		battlecry.setTargetRequirement(targetSelection);
		return battlecry;
	}

	public Battlecry(ISpell spell) {
		this.spell = spell;
		setActionType(ActionType.MINION_ABILITY);
	}

	@Override
	public void execute(GameContext context, Player player) {
		context.getLogic().castSpell(player, spell, getTarget());
		
	}
}
