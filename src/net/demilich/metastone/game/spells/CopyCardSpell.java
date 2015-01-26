package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.CardLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyCardSpell extends Spell {

	public static SpellDesc create(CardLocation cardLocation, int numberOfCardsToCopy) {
		SpellDesc desc = new SpellDesc(CopyCardSpell.class);
		desc.set(SpellArg.CARD_LOCATION, cardLocation);
		desc.set(SpellArg.NUMBER_OF_CARDS, numberOfCardsToCopy);
		return desc;
	}
	
	private static Logger logger = LoggerFactory.getLogger(CopyCardSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		CardLocation cardLocation = (CardLocation) desc.get(SpellArg.CARD_LOCATION);
		int numberOfCardsToCopy = desc.getInt(SpellArg.NUMBER_OF_CARDS);
		
		Player opponent = context.getOpponent(player);
		CardCollection source = null;
		switch (cardLocation) {
		case DECK:
			source = opponent.getDeck();
			break;
		case HAND:
			source = opponent.getHand();
			break;
		default:
			logger.error("Trying to copy cards from invalid cardLocation {}", cardLocation);
			break;
		}

		for (int i = 0; i < numberOfCardsToCopy; i++) {
			if (source.isEmpty()) {
				return;
			}
			Card clone = source.getRandom().clone();
			context.getLogic().receiveCard(player.getId(), clone);
		}
	}

}
