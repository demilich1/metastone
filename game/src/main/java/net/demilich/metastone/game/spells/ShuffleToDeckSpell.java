package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;

public class ShuffleToDeckSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Card card = null;
		if (target != null) {
			card = ((Actor) target).getSourceCard().getCopy();
		} else if (desc.contains(SpellArg.CARD_FILTER)){
			EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
			CardCollection cards = CardCatalogue.query(context.getDeckFormat());
			CardCollection result = new CardCollection();
			for (Card cardResult : cards) {
				if (cardFilter.matches(context, player, cardResult)) {
					result.add(cardResult);
				}
			}
			card = result.getRandom();
		} else {
			String cardId = (String) desc.get(SpellArg.CARD);
			card = context.getCardById(cardId);						
		}

		int howMany = desc.getValue(SpellArg.HOW_MANY, context, player, target, source, 1);
		for (int i = 0; i < howMany; i++) {
			if (card != null) {
				context.getLogic().shuffleToDeck(player, card.clone());
			}
		}
	}

}
