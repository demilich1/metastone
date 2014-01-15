package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;

public class MinMaxBehaviour implements IBehaviour {
	
	private final static Logger logger = LoggerFactory.getLogger(MinMaxBehaviour.class);

	@Override
	public Entity provideTargetFor(Player player, GameAction action, List<Entity> validTargets) {
		//TODO: copied from PlayRandomBehaviour
		if (validTargets.isEmpty()) {
			return null;
		}

		Entity randomTarget = validTargets.get(ThreadLocalRandom.current().nextInt(validTargets.size()));
		logger.debug(player.getName() + " picks random target: " + randomTarget.getName());
		return randomTarget;
	}

	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
		GameAction bestAction = null;
		int bestScore = calculateGameStateScore(context, player.getId());
		logger.debug("Current game state has a score of {}", bestScore);
		for (GameAction gameAction : validActions) {
			if (gameAction.getValidTargets() == null) {
				logger.debug("Context before simulation\n"+context.toString());
				GameContext simulationResult = simulateAction(context, player, gameAction, null);
				logger.debug("Context after simulation\n"+context.toString());
				logger.debug("Simulation result\n"+simulationResult.toString());
				int gameStateScore = calculateGameStateScore(simulationResult, player.getId());
				logger.debug("Action (no target) gains score of {}", gameStateScore);
				if (gameStateScore > bestScore) {
					bestScore = gameStateScore;
					bestAction = gameAction;
				}
			} else {
				for (Entity target : gameAction.getValidTargets()) {
					logger.debug("Context before simulation\n"+context.toString());
					GameContext simulationResult = simulateAction(context, player, gameAction, target);
					logger.debug("Context after simulation\n"+context.toString());
					logger.debug("Simulation result\n"+simulationResult.toString());
					int gameStateScore = calculateGameStateScore(simulationResult, player.getId());
					logger.debug("Action (target {}) gains score of {}", target, gameStateScore);
					if (gameStateScore > bestScore) {
						bestScore = gameStateScore;
						bestAction = gameAction;
						gameAction.setTarget(target);
					}
				}

			}
		}
		return bestAction;
	}

	private GameContext simulateAction(GameContext context, Player player, GameAction action, Entity target) {
		GameContext simulation = context.clone();
		GameLogic.logger.debug("********SIMULATION starts**********");
		if (target != null) {
			action.setTarget(target);
		}
		
		simulation.getLogic().performGameAction(player.getId(), action);
		GameLogic.logger.debug("********SIMULATION ends**********");
		return simulation;
	}

	private int calculateGameStateScore(GameContext context, int playerId) {
		int score = 0;
		Player player = context.getPlayer(playerId);
		Player opponent = context.getOpponent(player);
		if (player.getHero().isDead()) {
			return Integer.MIN_VALUE;
		} 
		if (opponent.getHero().isDead()) {
			return Integer.MAX_VALUE;
		} 
		int ownHp = player.getHero().getHp() + player.getHero().getArmor();
		int opponentHp = opponent.getHero().getHp() + opponent.getHero().getArmor();
		score += ownHp - opponentHp;
		
		score += player.getHand().getCount();
		score -= opponent.getHand().getCount();
		for (Minion minion : player.getMinions()) {
			score += calculateMinionScore(minion);
		}

		for (Minion minion : opponent.getMinions()) {
			score -= calculateMinionScore(minion);
		}
		return score;
	}

	private int calculateMinionScore(Minion minion) {
		float minionScore = minion.getAttack() + minion.getHp();
		if (minion.hasTag(GameTag.TAUNT)) {
			minionScore += 2;
		}
		if (minion.hasTag(GameTag.WINDFURY)) {
			minionScore += minion.getAttack() * 0.5f;
		}
		if (minion.hasTag(GameTag.DIVINE_SHIELD)) {
			minionScore *= 1.5f;
		}
		if (minion.hasTag(GameTag.SPELL_POWER)) {
			minionScore += minion.getTagValue(GameTag.SPELL_POWER);
		}
		if (minion.hasTag(GameTag.ENRAGED)) {
			minionScore += 1;
		}
		if (minion.hasTag(GameTag.STEALTHED)) {
			minionScore += 1;
		}
		if (minion.hasTag(GameTag.DEATHRATTLE)) {
			minionScore += 1;
		}
		return (int) minionScore;
	}

}
