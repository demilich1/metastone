package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;

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
			} else if (gameAction.getActionType() == ActionType.SPELL) {
				PlayCardAction playCardAction = (PlayCardAction) gameAction;
				if (playCardAction.getCard() instanceof SecretCard) {
					return gameAction;
				}
				
			}
		}
		return null;
	}

}
