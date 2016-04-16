package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;

public class CardPropertyCondition extends Condition {

	public CardPropertyCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity target) {
		Card card = null;
		if (target instanceof Card) {
			card = (Card) target;
		} else if (target instanceof Actor) {
			Actor actor = (Actor) target;
			card = actor.getSourceCard();
		}

		if (card == null) {
			return false;
		}

		CardType cardType = (CardType) desc.get(ConditionArg.CARD_TYPE);
		if (cardType != null && !card.getCardType().isCardType(cardType)) {
			return false;
		}

		String cardId = (String) desc.get(ConditionArg.CARD_ID);
		if (cardId != null && !card.getCardId().contains(cardId)) {
			return false;
		}

		return true;
	}

}
