package net.demilich.metastone.game.spells.desc.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;

public class SpecificCardFilter extends EntityFilter {
	
	Logger logger = LoggerFactory.getLogger(SpecificCardFilter.class);

	public SpecificCardFilter(FilterDesc desc) {
		super(desc);
	}

	@Override
	protected boolean test(GameContext context, Player player, Entity entity) {
		String cardId = null;
		if (entity instanceof Card) {
			cardId = ((Card) entity).getCardId();
		} else if (entity instanceof Actor) {
			if (((Actor) entity).getSourceCard() == null) {
				return false;
			}
			cardId = ((Actor) entity).getSourceCard().getCardId();
		}

		String requiredCardId = desc.getString(FilterArg.CARD_ID);
		return cardId.contains(requiredCardId);
	}

}
