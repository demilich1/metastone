package net.demilich.metastone.game.behaviour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;

public class FlatMonteCarlo extends Behaviour {

	private final static Logger logger = LoggerFactory.getLogger(FlatMonteCarlo.class);

	private int iterations;

	public FlatMonteCarlo(int iterations) {
		this.iterations = iterations;
	}

	private GameAction getBestAction(HashMap<GameAction, Double> actionScores) {
		GameAction bestAction = null;
		double bestScore = Integer.MIN_VALUE;
		for (GameAction actionEntry : actionScores.keySet()) {
			double score = actionScores.get(actionEntry);
			if (score > bestScore) {
				bestAction = actionEntry;
				bestScore = score;
			}
		}
		logger.debug("Best action determined by MonteCarlo: " + bestAction.getActionType());
		return bestAction;
	}

	@Override
	public String getName() {
		return "Flat Monte-Carlo " + iterations;
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

	private int playRandomUntilEnd(GameContext simulation, int playerId) {
		for (Player player : simulation.getPlayers()) {
			player.setBehaviour(new PlayRandomBehaviour());
		}
		simulation.playFromState();
		return simulation.getWinningPlayerId() == playerId ? 1 : 0;
	}

	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
		if (validActions.size() == 1) {
			return validActions.get(0);
		}
		HashMap<GameAction, Double> actionScores = new HashMap<>();
		for (GameAction gameAction : validActions) {
			double score = simulate(context, player.getId(), gameAction);
			actionScores.put(gameAction, score);
			logger.debug("Action {} gets score of {}", gameAction.getActionType(), score);

		}
		GameAction bestAction = getBestAction(actionScores);
		return bestAction;
	}

	private double simulate(GameContext context, int playerId, GameAction action) {
		GameContext simulation = context.clone();
		simulation.getLogic().performGameAction(simulation.getActivePlayer().getId(), action);
		if (simulation.gameDecided()) {
			return simulation.getWinningPlayerId() == playerId ? 1 : 0;
		}
		double score = 0;
		for (int i = 0; i < iterations; i++) {
			score += playRandomUntilEnd(simulation.clone(), playerId);
		}
		return score;
	}

}
