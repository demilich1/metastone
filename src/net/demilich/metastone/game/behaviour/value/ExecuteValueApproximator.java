package net.demilich.metastone.game.behaviour.value;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.entities.minions.Minion;

public class ExecuteValueApproximator implements IValueApproximator {

	@Override
	public float getValue(GameContext context, GameAction action, int playerId) {
		// Execute target has to be a minion
		Minion minion = (Minion) context.resolveSingleTarget(action.getTargetKey());
		int factor = minion.getOwner() == playerId ? -1 : 1;
		return Values.getMinionValue(minion) * factor;
	}

}
