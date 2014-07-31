package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;

public class EndTurnAction extends GameAction {
	
	public EndTurnAction() {
		setActionType(ActionType.END_TURN);
	}

	@Override
	public void execute(GameContext context, int playerId) {
		context.endTurn(playerId);
	}
	
	@Override
	public String toString() {
		return String.format("[%s]", getActionType());
	}

}
