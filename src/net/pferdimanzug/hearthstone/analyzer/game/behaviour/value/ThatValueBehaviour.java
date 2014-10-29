package net.pferdimanzug.hearthstone.analyzer.game.behaviour.value;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.Behaviour;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThatValueBehaviour extends Behaviour {
	
	private static Logger logger = LoggerFactory.getLogger(ThatValueBehaviour.class);
	
	private final IValueApproximator valueApproximator = new ValueApproximator();

	@Override
	public String getName() {
		return "Dat value";
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
