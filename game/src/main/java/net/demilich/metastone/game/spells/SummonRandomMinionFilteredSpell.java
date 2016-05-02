package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;

public class SummonRandomMinionFilteredSpell extends Spell {

	protected static MinionCard getRandomMatchingMinionCard(GameContext context, Player player, EntityFilter cardFilter, boolean includeUncollictible) {
		CardCollection relevantMinions = null;
		if (includeUncollictible) {
			relevantMinions = CardCatalogue.query(card -> cardFilter.matches(context, player, card));
		} else {
			CardCollection allMinions = CardCatalogue.query(context.getDeckFormat(), CardType.MINION);
			relevantMinions = new CardCollection();
			for (Card card : allMinions) {
				if (cardFilter.matches(context, player, card)) {
					relevantMinions.add(card);
				}
			}
		}
		
		return (MinionCard) relevantMinions.getRandom();
	}


	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		boolean includeUncollectible = desc.getBool(SpellArg.INCLUDE_UNCOLLECTIBLE);
				
		int boardPosition = SpellUtils.getBoardPosition(context, player, desc, source);
		MinionCard minionCard = getRandomMatchingMinionCard(context, player, cardFilter, includeUncollectible);
		if (minionCard != null) {
			context.getLogic().summon(player.getId(), minionCard.summon(), null, boardPosition, false);
		}
	}

}
