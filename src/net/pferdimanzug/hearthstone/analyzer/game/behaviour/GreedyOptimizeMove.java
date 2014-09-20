package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.heuristic.IGameStateHeuristic;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreedyOptimizeMove extends Behaviour {

	private final static Logger logger = LoggerFactory.getLogger(GreedyOptimizeMove.class);

	private final IGameStateHeuristic heuristic;

	public GreedyOptimizeMove(IGameStateHeuristic heuristic) {
		this.heuristic = heuristic;
	}

	@Override
	public String getName() {
		return "Greedy Best Move";
	}

	@Override
	public List<Card> mulligan(GameContext context, Player player, List<Card> cards) {
		List<Card> discardedCards = new ArrayList<Card>();
		for (Card card : cards) {
			if (card.getBaseManaCost() >= 4) {
				discardedCards.add(card);
			}
		}
		return discardedCards;
	}

	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
		if (validActions.size() == 1) {
			return validActions.get(0);
		}
		GameAction bestAction = validActions.get(0);
		double bestScore = Double.NEGATIVE_INFINITY;
		logger.debug("Current game state has a score of {}", bestScore, hashCode());
		for (GameAction gameAction : validActions) {
			GameContext simulationResult = simulateAction(context.clone(), player, gameAction);
			double gameStateScore = heuristic.getScore(simulationResult, player.getId());
			logger.debug("Action {} gains score of {}", gameAction, gameStateScore);
			if (gameStateScore > bestScore) {
				bestScore = gameStateScore;
				bestAction = gameAction;
				logger.debug("BEST ACTION SO FAR id:{}", bestAction.hashCode());
			}
			simulationResult.dispose();

		}
		logger.debug("Performing best action: {}", bestAction);

		return bestAction;
	}

	private GameContext simulateAction(GameContext simulation, Player player, GameAction action) {
		GameLogic.logger.debug("");
		GameLogic.logger.debug("********SIMULATION starts********** " + simulation.getLogic().hashCode());
		simulation.getLogic().performGameAction(player.getId(), action);
		GameLogic.logger.debug("********SIMULATION ends**********");
		GameLogic.logger.debug("");
		return simulation;
	}

}
