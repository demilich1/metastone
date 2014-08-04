package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.heuristic.IGameStateHeuristic;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreedyOptimizeTurn extends Behaviour {
	
	private final static Logger logger = LoggerFactory.getLogger(GreedyOptimizeTurn.class);
	
	private final IGameStateHeuristic heuristic;

	public GreedyOptimizeTurn(IGameStateHeuristic heuristic) {
		this.heuristic = heuristic;
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
		GameAction bestAction = null;
		
		
		return bestAction;
	}

	private int simulateAction(GameContext simulation, int playerId, GameAction action, Entity target) {
		if (target != null) {
			action.setTarget(target);
		}
		
		simulation.getLogic().performGameAction(playerId, action);
		List<GameAction> validActions = simulation.getValidActions();
		if (validActions.size() == 1) {
			return heuristic.getScore(simulation, playerId);
		} 
		int accumulatedScore = 0;
		for (GameAction gameAction : validActions) {
			//accumulatedScore += si
		}
		return accumulatedScore;
	}

}
