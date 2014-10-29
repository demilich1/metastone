package net.pferdimanzug.hearthstone.analyzer.game.behaviour.value;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;

public interface IValueApproximator {

	public abstract float getValue(GameContext context, GameAction action, int playerId);

}