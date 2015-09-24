package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;

public class ReceiveRandomCollectibleCardSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		CardCollection cards = CardCatalogue.query((CardType) null);
		CardCollection result = new CardCollection();
		for (Card card : cards) {
			if (cardFilter.matches(context, player, card)) {
				result.add(card);
			}
		}

		int count = desc.getInt(SpellArg.HOW_MANY, 1);
		int manaCostModifier = desc.getInt(SpellArg.MANA_MODIFIER, 0);
		for (int i = 0; i < count; i++) {
			Card card = result.getRandom();
			if (manaCostModifier != 0) {
				card.setAttribute(Attribute.MANA_COST_MODIFIER, manaCostModifier);
			}
			context.getLogic().receiveCard(player.getId(), card.clone());
		}
	}

}
