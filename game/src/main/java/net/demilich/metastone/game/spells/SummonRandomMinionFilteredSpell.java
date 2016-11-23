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
import net.demilich.metastone.game.spells.desc.source.CardSource;

public class SummonRandomMinionFilteredSpell extends Spell {

	protected static MinionCard getRandomMatchingMinionCard(GameContext context, Player player, EntityFilter cardFilter, CardSource cardSource) {
		CardCollection relevantMinions = null;
		if (cardSource != null) {
			CardCollection allCards = cardSource.getCards(context, player);
			relevantMinions = new CardCollection();
			for (Card card : allCards) {
				if (card.getCardType().isCardType(CardType.MINION) && (cardFilter == null || cardFilter.matches(context, player, card))) {
					relevantMinions.add(card);
				}
			}
		} else {
			CardCollection allMinions = CardCatalogue.query(context.getDeckFormat(), CardType.MINION);
			relevantMinions = new CardCollection();
			for (Card card : allMinions) {
				if (cardFilter == null || cardFilter.matches(context, player, card)) {
					relevantMinions.add(card);
				}
			}
		}
		
		return (MinionCard) relevantMinions.getRandom();
	}


	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		String replacementCard = (String) desc.get(SpellArg.CARD);
		CardSource cardSource = (CardSource) desc.get(SpellArg.CARD_SOURCE);

		int boardPosition = SpellUtils.getBoardPosition(context, player, desc, source);
		MinionCard minionCard = getRandomMatchingMinionCard(context, player, cardFilter, cardSource);
		if (minionCard == null && replacementCard != null) {
			minionCard = (MinionCard) context.getCardById(replacementCard);
		}
		if (minionCard != null) {
			context.getLogic().summon(player.getId(), minionCard.summon(), null, boardPosition, false);
		}
	}

}
