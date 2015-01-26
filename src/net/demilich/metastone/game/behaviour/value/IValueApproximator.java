package net.demilich.metastone.game.behaviour.value;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.GameAction;

public interface IValueApproximator {

	public abstract float getValue(GameContext context, GameAction action, int playerId);

}