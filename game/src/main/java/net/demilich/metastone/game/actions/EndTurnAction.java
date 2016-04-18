package net.demilich.metastone.game.actions;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.targeting.TargetSelection;

public class EndTurnAction extends GameAction {

	public EndTurnAction() {
		setActionType(ActionType.END_TURN);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public void execute(GameContext context, int playerId) {
		context.endTurn();
	}

	@Override
	public String getPromptText() {
		return "[End turn]";
	}

	@Override
	public boolean isSameActionGroup(GameAction anotherAction) {
		return anotherAction.getActionType() == getActionType();
	}

	@Override
	public String toString() {
		return String.format("[%s]", getActionType());
	}

}
