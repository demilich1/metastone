package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardLocation;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.IdFactory;

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
		case HERO_POWER:
		case PENDING:
		default:
			logger.error("Trying to copy cards from invalid cardLocation {}", cardLocation);
			break;
		}

		for (int i = 0; i < numberOfCardsToCopy; i++) {
			if (source.isEmpty()) {
				return;
			}
			context.getLogic().receiveCard(player.getId(), source.getRandom().clone());
		}
	}

}
