package net.demilich.metastone.gui.sandboxmode.actions;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.targeting.TargetSelection;

public class KillAction extends GameAction {

	public KillAction() {
		setTargetRequirement(TargetSelection.MINIONS);
		setActionType(ActionType.SYSTEM);
	}

	@Override
	public void execute(GameContext context, int playerId) {
		Actor target = (Actor) context.resolveSingleTarget(getTargetKey());
		context.getLogic().markAsDestroyed(target);
	}

	@Override
	public String getPromptText() {
		return "[Kill]";
	}

	@Override
	public boolean isSameActionGroup(GameAction anotherAction) {
		return anotherAction instanceof KillAction;
	}

}
