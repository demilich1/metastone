package net.pferdimanzug.hearthstone.analyzer.game.behaviour.learning;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.Behaviour;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

public class LearningBehaviour extends Behaviour {
	
	private static Logger logger = LoggerFactory.getLogger(LearningBehaviour.class);
	
	private static final String SAVE_PATH = "brain.ser"; 

	private static final IBrain brain = new Brain();
	
	public LearningBehaviour(boolean learn) {
		brain.load(SAVE_PATH);
		brain.setLearning(learn);
	}

	@Override
	public String getName() {
		return "Learning";
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

		// for now, do not evaluate battecry actions
		if (validActions.get(0).getActionType() == ActionType.BATTLECRY) {
			return validActions.get(context.getLogic().random(validActions.size()));
		}

		GameAction bestAction = null;
		double expectedUtility = -1.0;
		double[] nextOutput = null;

		for (GameAction gameAction : validActions) {
			GameContext simulation = context.clone();
			simulation.getLogic().performGameAction(player.getId(), gameAction);
			double[] output = brain.getOutput(simulation, player.getId());
			double utility = brain.getEstimatedUtility(output);
			
			if (utility > expectedUtility) {
				bestAction = gameAction;
				expectedUtility = utility;
				nextOutput = output;
				//logger.info("Action {} received utility of {}", gameAction, utility);
			}
		}

		if (brain.isLearning()) {
			brain.learn(context, player.getId(), nextOutput);
		}

		return bestAction;
	}

	@Override
	public void onGameOver(GameContext context, int playerId, int winningPlayerId) {
		if (!brain.isLearning()) {
			return;
		}

		double[] actual = new double[1];
		if (playerId == winningPlayerId) {
			actual[0] = 1.0;
		} else {
			actual[0] = 0.0;
		}

		brain.learn(context, playerId, actual);
	}
	
	public void save() {
		brain.save(SAVE_PATH);
	}

}
