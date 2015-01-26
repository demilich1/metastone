package net.demilich.metastone.game.behaviour.value;

import java.util.ArrayList;
import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.behaviour.Behaviour;
import net.demilich.metastone.game.cards.Card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionValueBehaviour extends Behaviour {
	
	private static Logger logger = LoggerFactory.getLogger(ActionValueBehaviour.class);
	
	private final IValueApproximator valueApproximator = new ValueApproximator();

	@Override
	public String getName() {
		return "Action-value";
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
			return validActions.get(0);
		}
		GameAction bestAction = validActions.get(0);
		float bestScore = Float.NEGATIVE_INFINITY;
		for (GameAction gameAction : validActions) {
			float score = valueApproximator.getValue(context, gameAction, player.getId());
			logger.debug("Action {} gains score of {}", gameAction, score);
			if (score > bestScore) {
				bestScore = score;
				bestAction = gameAction;
			}

		}
		logger.debug("Performing best action: {} with score of {}", bestAction, bestScore);

		return bestAction;
	}

}
