package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;

public class FromDeckToHandSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		if (target != null && target.getEntityType() == EntityType.CARD) {
			Card card = (Card) target;
			context.getLogic().drawCard(player.getId(), card, source);
			return;
		}

		int value = desc.getValue(SpellArg.VALUE, context, player, target, source, 0);
		String replacementCard = (String) desc.get(SpellArg.CARD);

		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		CardCollection relevantCards = null;
		if (cardFilter != null) {
			relevantCards = SpellUtils.getCards(player.getDeck(), card -> cardFilter.matches(context, player, card));
		} else {
			relevantCards = SpellUtils.getCards(player.getDeck(), null);
		}
		for (int i = 0; i < value; i++) {
			Card card = null;
			if (!relevantCards.isEmpty()) {
				card = relevantCards.getRandom();
				relevantCards.remove(card);
				player.getDeck().remove(card);
			} else if (replacementCard != null) {
				card = CardCatalogue.getCardById(replacementCard);
			}

			if (card != null) {
				context.getLogic().receiveCard(player.getId(), card);
			}
		}
	}

}
