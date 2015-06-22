package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;

public class MatchesCardCondition extends Condition {

	public MatchesCardCondition(ConditionDesc desc) {
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
		
		if (card == null || card.getCardId() == null) {
			return false;
		}
		
		String cardName = (String) desc.get(ConditionArg.CARD_NAME);
		
		return card.getCardId().contains(cardName);
	}

}
