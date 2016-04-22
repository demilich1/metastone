package net.demilich.metastone.game.spells;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.CardLocation;

public class CopyCardSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(CopyCardSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int numberOfCardsToCopy = desc.getValue(SpellArg.VALUE, context, player, target, source, 1);
		if (target != null) {
			Card targetCard = null;
			if (target.getEntityType() == EntityType.CARD) {
				targetCard = (Card) target;
			} else if (target.getEntityType() == EntityType.MINION) {
				Minion minion = (Minion) target;
				targetCard = minion.getSourceCard();
			}
			for (int i = 0; i < numberOfCardsToCopy; i++) {
				context.getLogic().receiveCard(player.getId(), targetCard.getCopy());
			}
			return;
		}

		CardLocation cardLocation = (CardLocation) desc.get(SpellArg.CARD_LOCATION);

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
			Card clone = sourceCollection.getRandom().getCopy();
			context.getLogic().receiveCard(player.getId(), clone);
		}
	}

}
