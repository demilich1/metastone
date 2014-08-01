package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
	public String toString() {
		return String.format("[%s]", getActionType());
	}

}
