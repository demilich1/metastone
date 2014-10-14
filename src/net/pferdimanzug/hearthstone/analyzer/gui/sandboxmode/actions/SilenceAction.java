package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SilenceSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class SilenceAction extends GameAction {

	public SilenceAction() {
		setTargetRequirement(TargetSelection.MINIONS);
		setActionType(ActionType.BATTLECRY);
	}

	@Override
	public void execute(GameContext context, int playerId) {
		SpellDesc silenceSpell = SilenceSpell.create();
		silenceSpell.setTarget(getTargetKey());
		context.getLogic().castSpell(playerId, silenceSpell);
	}

	@Override
	public String getPromptText() {
		return "[Silence]";
	}

	@Override
	public boolean isSameActionGroup(GameAction anotherAction) {
		// TODO Auto-generated method stub
		return false;
	}

}
