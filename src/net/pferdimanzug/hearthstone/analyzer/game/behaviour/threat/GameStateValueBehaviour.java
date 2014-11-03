package net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.Behaviour;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.GreedyOptimizeTurn;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.IBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.heuristic.IGameStateHeuristic;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameStateValueBehaviour extends Behaviour {

	private final Logger logger = LoggerFactory.getLogger(GreedyOptimizeTurn.class);

	private final IGameStateHeuristic heuristic;

	public GameStateValueBehaviour() {
		this.heuristic = new ThreatBasedHeuristic();
	}

	private double alphaBeta(GameContext context, int playerId, GameAction action, int depth) {
		GameContext simulation = context.clone();
		simulation.getLogic().performGameAction(playerId, action);
		if (depth == 0 || simulation.getActivePlayerId() != playerId || simulation.gameDecided()) {
			return heuristic.getScore(simulation, playerId);
		}

		List<GameAction> validActions = simulation.getValidActions();

		double score = Float.NEGATIVE_INFINITY;
		
		for (GameAction gameAction : validActions) {
			score = Math.max(score, alphaBeta(simulation, playerId, gameAction, depth - 1));
			if (score >= 10000) {
				break;
			}
		}

		return score;
	}

	@Override
	public IBehaviour clone() {
		return new GameStateValueBehaviour();
	}

	@Override
	public String getName() {
		return "Game state value (2 turns look-ahead)";
	}

	@Override
	public List<Card> mulligan(GameContext context, Player player, List<Card> cards) {
		List<Card> discardedCards = new ArrayList<Card>();
		for (Card card : cards) {
			if (card.getBaseManaCost() > 3) {
				discardedCards.add(card);
			}
		}
		return discardedCards;
	}

	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
		if (validActions.size() == 1) {
			heuristic.onActionSelected(context, player.getId());
			return validActions.get(0);
		}

		int depth = 2;
		// when evaluating battlecry actions, only optimize the immediate value
		if (validActions.get(0).getActionType() == ActionType.BATTLECRY) {
			depth = 0;
		}

		GameAction bestAction = validActions.get(0);
		double bestScore = Double.NEGATIVE_INFINITY;

		for (GameAction gameAction : validActions) {
			double score = alphaBeta(context, player.getId(), gameAction, depth);
			if (score > bestScore) {
				bestAction = gameAction;
				bestScore = score;
			}
		}

		logger.debug("Selecting best action {} with score {}", bestAction, bestScore);
		heuristic.onActionSelected(context, player.getId());

		return bestAction;
	}

}
