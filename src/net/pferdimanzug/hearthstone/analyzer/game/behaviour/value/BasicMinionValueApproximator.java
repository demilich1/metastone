package net.pferdimanzug.hearthstone.analyzer.game.behaviour.value;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayMinionCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class BasicMinionValueApproximator implements IValueApproximator {

	@Override
	public float getValue(GameContext context, GameAction action, int playerId) {
		PlayMinionCardAction minionCardAction = (PlayMinionCardAction) action;
		Card card = context.resolveCardReference(minionCardAction.getCardReference());
		MinionCard minionCard = (MinionCard) card;
		Player player = context.getPlayer(playerId);
		Player opponent = context.getOpponent(player);
		boolean opponentCanTrade = false;
		for (Minion minion : opponent.getMinions()) {
			if (minion.getAttack() >= minionCard.getBaseHp()) {
				opponentCanTrade = true;
				break;
			}
		}
		float value = Values.getMinionCardValue(minionCard);
		if (!opponentCanTrade) {
			value +=1;
		}
		return value;
	}

}
