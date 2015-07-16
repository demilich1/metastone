package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;

public class GraveyardContainsCondition extends Condition {

	public GraveyardContainsCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity target) {
		String cardId = (String) desc.get(ConditionArg.CARD_NAME);
		for (Entity deadEntity : player.getGraveyard()) {
			Card card = null;
			if (deadEntity instanceof Actor) {
				Actor actor = (Actor) deadEntity;
				card = actor.getSourceCard();
			} else if (deadEntity instanceof Card) {
				card = (Card) deadEntity;
			}
			
			if (card == null || card.getCardId() == null) {
				System.out.println("Graveyard card check is NULL: " + card);
			}
			
			if (card.getCardId().equals(cardId)) {
				return true;
			}
		}
		return false;
	}

}
