package net.demilich.metastone.game.behaviour.value;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.actions.PlayMinionCardAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.minions.Minion;

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
