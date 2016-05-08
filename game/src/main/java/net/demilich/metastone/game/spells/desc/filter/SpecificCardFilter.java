package net.demilich.metastone.game.spells.desc.filter;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;

public class SpecificCardFilter extends EntityFilter {
	
	public SpecificCardFilter(FilterDesc desc) {
		super(desc);
	}

	@Override
	protected boolean test(GameContext context, Player player, Entity entity) {
		String cardId = null;
		if (entity instanceof Card) {
			cardId = ((Card) entity).getCardId();
		} else if (entity instanceof Actor) {
			cardId = ((Actor) entity).getSourceCard().getCardId();
		}

		String requiredCardId = desc.getString(FilterArg.CARD_ID);
		return cardId.equalsIgnoreCase(requiredCardId);
	}

}
