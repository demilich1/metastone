package net.demilich.metastone.game.spells;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.CardLocation;

public class CopyCardSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(CopyCardSpell.class);
	
	public static SpellDesc create(CardLocation cardLocation, int numberOfCardsToCopy) {
		Map<SpellArg, Object> arguments = SpellDesc.build(CopyCardSpell.class);
		arguments.put(SpellArg.CARD_LOCATION, cardLocation);
		arguments.put(SpellArg.VALUE, numberOfCardsToCopy);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		CardLocation cardLocation = (CardLocation) desc.get(SpellArg.CARD_LOCATION);
		int numberOfCardsToCopy = desc.getInt(SpellArg.VALUE, 1);
		
		Player opponent = context.getOpponent(player);
		CardCollection sourceCollection = null;
		switch (cardLocation) {
		case DECK:
			sourceCollection = opponent.getDeck();
			break;
		case HAND:
			sourceCollection = opponent.getHand();
			break;
		default:
			logger.error("Trying to copy cards from invalid cardLocation {}", cardLocation);
			break;
		}

		for (int i = 0; i < numberOfCardsToCopy; i++) {
			if (sourceCollection.isEmpty()) {
				return;
			}
			Card clone = sourceCollection.getRandom().clone();
			context.getLogic().receiveCard(player.getId(), clone);
		}
	}

}
