package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class KillAction extends GameAction {

	public KillAction() {
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public void execute(GameContext context, int playerId) {
		SpellDesc destroySpell = DestroySpell.create();
		destroySpell.setTarget(getTargetKey());
		context.getLogic().castSpell(playerId, destroySpell);
	}

	@Override
	public boolean isSameActionGroup(GameAction anotherAction) {
		return anotherAction instanceof KillAction;
	}

	@Override
	public String getPromptText() {
		return "[Kill]";
	}

}
