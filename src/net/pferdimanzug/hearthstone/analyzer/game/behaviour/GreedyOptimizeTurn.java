package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.heuristic.IGameStateHeuristic;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreedyOptimizeTurn extends Behaviour {

	private final Logger logger = LoggerFactory.getLogger(GreedyOptimizeTurn.class);

	private final IGameStateHeuristic heuristic;

	private int assignedGC;
	private final HashMap<ActionType, Integer> evaluatedActions = new HashMap<ActionType, Integer>();
	private final TranspositionTable table = new TranspositionTable();

	public GreedyOptimizeTurn(IGameStateHeuristic heuristic) {
		this.heuristic = heuristic;
	}

	@Override
	public IBehaviour clone() {
		return new GreedyOptimizeTurn(heuristic);
	}

	@Override
	public String getName() {
		return "Min-Max Turn";
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

		// for now, do now evaluate battecry actions
		if (validActions.get(0).getActionType() == ActionType.BATTLECRY) {
			return validActions.get(context.getLogic().random(validActions.size()));
		}

		if (assignedGC != 0 && assignedGC != context.hashCode()) {
			logger.warn("AI behaviour was used in another context!");
		}

		assignedGC = context.hashCode();
		evaluatedActions.clear();
		table.clear();

		GameAction bestAction = null;
		int bestScore = Integer.MIN_VALUE;
		// int beta = heuristic.getScore(context, player.getId()) * 100;

		for (GameAction gameAction : validActions) {
			logger.debug("********************* SIMULATION STARTS *********************");
			// int score = simulateAction(context, player.getId(), gameAction);
			int score = alphaBeta(context, player.getId(), gameAction, 3);
			if (score > bestScore) {
				bestAction = gameAction;
				bestScore = score;
			}
			logger.debug("********************* SIMULATION ENDS, Action {} achieves score {}", gameAction, score);
		}

		int totalActionCount = 0;
		for (ActionType actionType : evaluatedActions.keySet()) {
			int count = evaluatedActions.get(actionType);
			logger.debug("{} actions of type {} have been evaluated this turn", count, actionType);
			totalActionCount += count;
		}
		logger.debug("{} actions in total have been evaluated this turn", totalActionCount);
		logger.debug("Selecting best action {} with score {}", bestAction, bestScore);

		return bestAction;
	}

	private int alphaBeta(GameContext context, int playerId, GameAction action, int depth) {
		GameContext simulation = context.clone();
		simulation.getLogic().performGameAction(playerId, action);
		if (!evaluatedActions.containsKey(action.getActionType())) {
			evaluatedActions.put(action.getActionType(), 0);
		}
		evaluatedActions.put(action.getActionType(), evaluatedActions.get(action.getActionType()) + 1);
		if (depth == 0 || simulation.getActivePlayerId() != playerId || simulation.gameDecided()) {
			return heuristic.getScore(simulation, playerId);
		}
		
		List<GameAction> validActions = simulation.getValidActions();

		int score = Integer.MIN_VALUE;
		if (table.known(simulation)) {
			return table.getScore(simulation);
			// logger.info("GameState is known, has score of {}", score);
		} else {
			for (GameAction gameAction : validActions) {
				score = Math.max(score, alphaBeta(simulation, playerId, gameAction, depth - 1));
				if (score == Integer.MAX_VALUE) {
					break;
				}
			}
			table.save(simulation, score);
		}

		return score;
	}

	private int simulateAction(GameContext context, int playerId, GameAction action) {
		GameContext simulation = context.clone();
		simulation.getLogic().performGameAction(playerId, action);
		if (!evaluatedActions.containsKey(action.getActionType())) {
			evaluatedActions.put(action.getActionType(), 0);
		}
		evaluatedActions.put(action.getActionType(), evaluatedActions.get(action.getActionType()) + 1);
		if (simulation.getActivePlayerId() != playerId || simulation.gameDecided()) {
			return heuristic.getScore(simulation, playerId);
		}
		List<GameAction> validActions = simulation.getValidActions();
		if (validActions.size() == 0) {
			throw new RuntimeException("No more possible moves, last action was: " + action);
		}
		int bestScore = Integer.MIN_VALUE;
		for (GameAction gameAction : validActions) {
			bestScore = Math.max(bestScore, simulateAction(simulation, playerId, gameAction));
		}
		return bestScore;
	}

}
