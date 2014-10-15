package net.pferdimanzug.hearthstone.analyzer.game.behaviour.mcts;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.Behaviour;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonteCarloTreeSearch extends Behaviour {
	
	private final static Logger logger = LoggerFactory.getLogger(MonteCarloTreeSearch.class);
	
	private static final int ITERATIONS = 500;

	@Override
	public String getName() {
		return "MCTS";
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
			//logger.info("MCTS selected best action {}", validActions.get(0));
			return validActions.get(0);
		}
		Node root = new Node(null, player.getId());
		root.initState(context, validActions);
		UctPolicy treePolicy = new UctPolicy();
		for (int i = 0; i < ITERATIONS; i++) {
			root.process(treePolicy);
		}
		GameAction bestAction = root.getBestAction();
		//logger.info("MCTS selected best action {}", bestAction);
		return bestAction;
	}

}
