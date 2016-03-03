package net.demilich.metastone.gui.sandboxmode.actions;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.targeting.TargetSelection;

public class SilenceAction extends GameAction {

	public SilenceAction() {
		setTargetRequirement(TargetSelection.MINIONS);
		setActionType(ActionType.SYSTEM);
	}

	@Override
	public void execute(GameContext context, int playerId) {
		Minion target = (Minion) context.resolveSingleTarget(getTargetKey());
		context.getLogic().silence(playerId, target);
	}

	@Override
	public String getPromptText() {
		return "[Silence]";
	}

	@Override
	public boolean isSameActionGroup(GameAction anotherAction) {
		return anotherAction instanceof SilenceAction;
	}

}
