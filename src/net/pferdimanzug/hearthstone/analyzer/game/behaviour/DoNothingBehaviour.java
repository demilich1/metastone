package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class DoNothingBehaviour implements IBehaviour {

	@Override
	public String getName() {
		return "Do Nothing";
	}

	@Override
	public Entity provideTargetFor(Player player, GameAction action) {
		List<Entity> validTargets = action.getValidTargets();
		if (validTargets.isEmpty()) {
			return null;
		}

		Entity randomTarget = validTargets.get(ThreadLocalRandom.current().nextInt(validTargets.size()));
		return randomTarget;
	}

	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
		return null;
	}

}
