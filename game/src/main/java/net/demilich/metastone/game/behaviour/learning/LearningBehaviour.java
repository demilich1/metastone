package net.demilich.metastone.game.behaviour.learning;

import java.util.ArrayList;
import java.util.List;



import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.behaviour.Behaviour;
import net.demilich.metastone.game.cards.Card;

public class LearningBehaviour extends Behaviour {

	//private static Logger logger = LoggerFactory.getLogger(LearningBehaviour.class);

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
	public void onGameOver(GameContext context, int playerId, int winningPlayerId) {
		if (!brain.isLearning()) {
			return;
		}

		double reward = 0;
		double[] actual = new double[1];
		if (playerId == winningPlayerId) {
			actual[0] = reward = 1.0;
		} else {
			actual[0] = reward = -1.0;
		}

		brain.learn(context, playerId, actual, reward);
	}

	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {

		// for now, do not evaluate battecry actions
		if (validActions.get(0).getActionType() == ActionType.BATTLECRY) {
			return validActions.get(context.getLogic().random(validActions.size()));
		}

		GameAction bestAction = null;
		double expectedUtility = -2.0;
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
				// logger.info("Action {} received utility of {}", gameAction,
				// utility);
			}
		}

		if (brain.isLearning()) {
			brain.learn(context, player.getId(), nextOutput, 0);
		}

		return bestAction;
	}

	public void save() {
		brain.save(SAVE_PATH);
	}

}
