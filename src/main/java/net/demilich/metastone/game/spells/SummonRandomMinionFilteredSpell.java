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

	protected static MinionCard getRandomMatchingMinionCard(GameContext context, Player player, EntityFilter cardFilter) {
		CardCollection allMinions = CardCatalogue.query(CardType.MINION);
		CardCollection relevantMinions = new CardCollection();
		for (Card card : allMinions) {
			if (cardFilter.matches(context, player, card)) {
				relevantMinions.add(card);
			}
		}
		return (MinionCard) relevantMinions.getRandom();
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		int boardPosition = desc.getInt(SpellArg.BOARD_POSITION_ABSOLUTE, -1);
		MinionCard minionCard = getRandomMatchingMinionCard(context, player, cardFilter);
		context.getLogic().summon(player.getId(), minionCard.summon(), null, boardPosition, false);
	}

}
