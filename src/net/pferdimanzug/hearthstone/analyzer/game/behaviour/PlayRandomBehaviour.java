package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayRandomBehaviour implements IBehaviour {

	private static Logger logger = LoggerFactory.getLogger(PlayRandomBehaviour.class);

	private Player player;

	public PlayRandomBehaviour(Player player) {
		this.player = player;
	}

	@Override
	public Entity provideTargetFor(GameAction action, List<Entity> validTargets) {
		if (validTargets.isEmpty()) {
			return null;
		}

		Entity randomTarget = validTargets.get(ThreadLocalRandom.current().nextInt(validTargets.size()));
		logger.debug(player.getName() + " picks random target: " + randomTarget.getName());
		return randomTarget;
	}

	@Override
	public GameAction requestAction(GameContext context, List<GameAction> validActions) {
		if (validActions.isEmpty()) {
			return null;
		}

		int randomIndex = ThreadLocalRandom.current().nextInt(validActions.size());
		GameAction randomAction = validActions.get(randomIndex);
		selectRandomTarget(randomAction);
		return randomAction;
	}
	
	private void selectRandomTarget(GameAction action) {
		List<Entity> validTargets = action.getValidTargets();
		if (validTargets == null || validTargets.isEmpty()) {
			return;
		}
		
		int randomIndex = ThreadLocalRandom.current().nextInt(validTargets.size());
		Entity randomTarget = validTargets.get(randomIndex);
		action.setTarget(randomTarget);
	}

}
