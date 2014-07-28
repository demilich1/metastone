package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class NoAggressionBehaviour implements IBehaviour {

	@Override
	public String getName() {
		return "No Aggression";
	}

	@Override
	public Entity provideTargetFor(Player player, GameAction action) {
		List<Entity> validTargets = action.getValidTargets();
		if (validTargets.isEmpty()) {
			return null;
		}

		Entity randomTarget = validTargets.get(ThreadLocalRandom.current().nextInt(validTargets.size()));
		return randomTarget;
	}

	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
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

	@Override
	public List<Card> mulligan(GameContext context, Player player, List<Card> cards) {
		return new ArrayList<Card>();
	}

}
