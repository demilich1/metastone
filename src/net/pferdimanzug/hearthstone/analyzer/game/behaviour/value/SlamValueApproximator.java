package net.pferdimanzug.hearthstone.analyzer.game.behaviour.value;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class SlamValueApproximator implements IValueApproximator {

	@Override
	public float getValue(GameContext context, GameAction action, int playerId) {
		Minion target = (Minion) context.resolveSingleTarget(action.getTargetKey());
		float value = 0;
		int factor = target.getOwner() == playerId ? -1 : 1;
		if (!Divination.willMinionDie(context, target, 2)) {
			value += Values.DRAW_CARD_VALUE;
			value += factor;
		} else {
			value += factor * Values.getMinionValue(target);	
		}
		
		return value;
	}

}
