package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;

public class DeckContainsCondition extends Condition {

	public DeckContainsCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity target) {
		String cardId = (String) desc.get(ConditionArg.CARD_ID);
		for (Card card : player.getDeck()) {
			if (card.getCardId().equalsIgnoreCase(cardId)) {
				return true;
			}
		}
		return false;
	}

}
