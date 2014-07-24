package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public interface IBehaviour {

	String getName();

	Entity provideTargetFor(Player player, GameAction action);

	GameAction requestAction(GameContext context, Player player, List<GameAction> validActions);
}
