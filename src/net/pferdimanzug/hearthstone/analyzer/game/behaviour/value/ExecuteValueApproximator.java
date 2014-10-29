package net.pferdimanzug.hearthstone.analyzer.game.behaviour.value;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class ExecuteValueApproximator implements IValueApproximator {

	@Override
	public float getValue(GameContext context, GameAction action, int playerId) {
		// Execute target has to be a minion
		Minion minion = (Minion) context.resolveSingleTarget(action.getTargetKey());
		int factor = minion.getOwner() == playerId ? -1 : 1;
		return Values.getMinionValue(minion) * factor;
	}

}
