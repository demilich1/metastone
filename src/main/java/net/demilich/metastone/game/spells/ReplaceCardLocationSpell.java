package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.targeting.CardLocation;

public class ReplaceCardLocationSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		CardCollection cards = CardCatalogue.query(context.getDeckFormat());
		CardCollection result = new CardCollection();
		String replacementCard = (String) desc.get(SpellArg.CARD);
		for (Card card : cards) {
			if (cardFilter.matches(context, player, card)) {
				result.add(card);
			}
		}
		
		CardLocation location = (CardLocation) desc.get(SpellArg.CARD_LOCATION);
		int count = 0;
		switch(location) {
		default:
		case DECK:
			count = player.getDeck().getCount();
			player.getDeck().removeAll();
			for (int i = 0; i < count; i++) {
				Card card = null;
				if (!result.isEmpty()) {
					card = result.getRandom();
				} else if (replacementCard != null) {
					card = CardCatalogue.getCardById(replacementCard);
				}
				if (card != null) {
					context.getLogic().shuffleToDeck(player, card.clone());
				}
			}
			break;
		case HAND:
			count = player.getHand().getCount();
			for (Card card : player.getHand().toList()) {
				context.getLogic().removeCard(player.getId(), card);
			}
			
			int manaCostModifier = desc.getValue(SpellArg.MANA_MODIFIER, context, player, target, source, 0);
			for (int i = 0; i < count; i++) {
				Card card = null;
				if (!result.isEmpty()) {
					card = result.getRandom();
				} else if (replacementCard != null) {
					card = CardCatalogue.getCardById(replacementCard);
				}

				if (manaCostModifier != 0) {
					card.setAttribute(Attribute.MANA_COST_MODIFIER, manaCostModifier);
				}
				if (card != null) {
					context.getLogic().receiveCard(player.getId(), card.clone());
				}
			}
			break;
		}
	}

}
