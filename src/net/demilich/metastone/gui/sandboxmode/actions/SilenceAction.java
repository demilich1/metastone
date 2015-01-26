package net.demilich.metastone.gui.sandboxmode.actions;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.spells.SilenceSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

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
