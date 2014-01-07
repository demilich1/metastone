package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public interface IBehaviour {
	public GameAction requestAction(GameContext context, List<GameAction> validActions);
	public Entity provideTargetFor(GameAction action, List<Entity> validTargets);

}
