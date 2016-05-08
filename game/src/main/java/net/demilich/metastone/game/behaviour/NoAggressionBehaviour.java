package net.demilich.metastone.game.behaviour;

import java.util.ArrayList;
import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;

public class NoAggressionBehaviour extends Behaviour {

	@Override
	public String getName() {
		return "No Aggression";
	}

	@Override
	public List<Card> mulligan(GameContext context, Player player, List<Card> cards) {
		return new ArrayList<Card>();
	}

	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
		if (validActions.size() == 1) {
			return validActions.get(0);
		}
		for (GameAction gameAction : validActions) {
			if (gameAction.getActionType() == ActionType.SUMMON) {
				return gameAction;
			}
		}
		return validActions.get(validActions.size() - 1);
	}

}
