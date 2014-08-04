package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

public class DoNothingBehaviour extends Behaviour {

	@Override
	public String getName() {
		return "Do Nothing";
	}

	@Override
	public List<Card> mulligan(GameContext context, Player player, List<Card> cards) {
		return new ArrayList<Card>();
	}

	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
		return validActions.get(validActions.size() - 1);
	}

}
