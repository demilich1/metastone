package net.pferdimanzug.hearthstone.analyzer.game.behaviour.mcts;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.IBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonteCarloTreeSearch implements IBehaviour {
	
	private final static Logger logger = LoggerFactory.getLogger(MonteCarloTreeSearch.class);
	
	private static final int ITERATIONS = 100;

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
	public Entity provideTargetFor(Player player, GameAction action) {
		// TODO: copied from PlayRandomBehaviour
		List<Entity> validTargets = action.getValidTargets();
		if (validTargets.isEmpty()) {
			return null;
		}

		Entity randomTarget = validTargets.get(ThreadLocalRandom.current().nextInt(validTargets.size()));
		if (randomTarget != null) {
			logger.debug(player.getName() + " picks random target: " + randomTarget.getName());
		}
		return randomTarget;
	}

	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
		if (validActions.size() == 0) {
			return validActions.get(0);
		}
		Node root = new Node(null);
		root.initState(context.clone(), validActions);
		root.expand();
		UctPolicy treePolicy = new UctPolicy();
		for (int i = 0; i < ITERATIONS; i++) {
			root.process(treePolicy);
		}
		Transition bestTransition = root.getBestAction();
		GameAction bestAction = bestTransition.getAction();
		Entity bestTarget = bestTransition.getTarget();
		if (bestTarget != null) {
			bestAction.setTarget(bestTarget);
		}
		logger.info("MCTS selected best action {}", bestAction);
		return bestAction;
	}

}
