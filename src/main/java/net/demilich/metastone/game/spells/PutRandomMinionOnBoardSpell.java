package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.CardLocation;

public class PutRandomMinionOnBoardSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		CardLocation cardLocation = (CardLocation) desc.get(SpellArg.CARD_LOCATION);
		putRandomMinionFromDeckOnBoard(context, player, cardFilter, cardLocation);
	}

	private void putRandomMinionFromDeckOnBoard(GameContext context, Player player, EntityFilter cardFilter, CardLocation cardLocation) {
		MinionCard minionCard = null;
		CardCollection collection = cardLocation == CardLocation.HAND ? player.getHand() : player.getDeck();
		if (!entityFilter) {
			minionCard = (MinionCard) collection.getRandomOfType(CardType.MINION);
		} else {
			minionCard = (MinionCard) SpellUtils.getRandomCard(collection, card -> cardFilter.matches(context, player, card));
		}

		if (minionCard == null) {
			return;
		}

		if (context.getLogic().summon(player.getId(), minionCard.summon())) {
			if (cardLocation == CardLocation.HAND) {
				context.getLogic().removeCard(player.getId(), minionCard);
			} else if (cardLocation == CardLocation.DECK) {
				context.getLogic().removeCardFromDeck(player.getId(), minionCard);
			}
		}
	}

}
