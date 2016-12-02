package net.demilich.metastone.game.spells;

import java.util.Map;

import co.paralleluniverse.fibers.Suspendable;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.spells.desc.source.CardSource;
import net.demilich.metastone.game.targeting.EntityReference;

public class DiscoverFilteredCardSpell extends Spell {
	
	public static SpellDesc create(EntityReference target, SpellDesc spell) {
		Map<SpellArg, Object> arguments = SpellDesc.build(DiscoverFilteredCardSpell.class);
		arguments.put(SpellArg.TARGET, target);
		arguments.put(SpellArg.SPELL, spell);
		return new SpellDesc(arguments);
	}
	
	@Override
	@Suspendable
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		CardCollection cards = CardCatalogue.query(context.getDeckFormat());
		CardSource cardSource = (CardSource) desc.get(SpellArg.CARD_SOURCE);
		if (cardSource != null) {
			cards = cardSource.getCards(context, player);
		}
		CardCollection result = new CardCollection();
		for (Card card : cards) {
			if (cardFilter == null || cardFilter.matches(context, player, card)) {
				result.add(card);
			}
		}
		cards = new CardCollection();
		
		int count = desc.getValue(SpellArg.HOW_MANY, context, player, target, source, 3);
		for (int i = 0; i < count; i++) {
			if (!result.isEmpty()) {
				Card card = null;
				do {
					card = result.getRandom();
					result.remove(card);
				} while (cards.containsCard(card));
				if (card != null) {
					cards.add(card);
				}
			}
		}
		
		if (!cards.isEmpty()) {
			SpellUtils.castChildSpell(context, player, SpellUtils.getDiscover(context, player, desc, cards).getSpell(), source, target);
		}
	}

}
