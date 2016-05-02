package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.targeting.CardLocation;

public class PutRandomMinionOnBoardSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		CardLocation cardLocation = (CardLocation) desc.get(SpellArg.CARD_LOCATION);
		if (cardLocation == null) {
			cardLocation = CardLocation.DECK;
		}
		int numberToSummon = desc.getValue(SpellArg.VALUE, context, player, target, source, 1);
		for (int i = 0; i < numberToSummon; i++) {
			putRandomMinionFromDeckOnBoard(context, player, cardFilter, cardLocation);
		}
	}

	private void putRandomMinionFromDeckOnBoard(GameContext context, Player player, EntityFilter cardFilter, CardLocation cardLocation) {
		MinionCard minionCard = null;
		CardCollection collection = cardLocation == CardLocation.HAND ? player.getHand() : player.getDeck();
		if (cardFilter == null) {
			minionCard = (MinionCard) collection.getRandomOfType(CardType.MINION);
		} else {
			minionCard = (MinionCard) SpellUtils.getRandomCard(collection, card -> cardFilter.matches(context, player, card));
		}

		if (minionCard == null) {
			return;
		}
		
		// we need to remove the card temporarily here, because there are card interactions like Starving Buzzard + Desert Camel
		// which could result in the card being drawn while a minion is summoned
		if (cardLocation == CardLocation.DECK) {
			player.getDeck().remove(minionCard);	
		}
		
		boolean summonSuccess = context.getLogic().summon(player.getId(), minionCard.summon());
		
		// re-add the card here if we removed it before
		if (cardLocation == CardLocation.DECK) {
			player.getDeck().add(minionCard);	
		}
		
		if (summonSuccess) {
			if (cardLocation == CardLocation.HAND) {
				context.getLogic().removeCard(player.getId(), minionCard);
			} else {
				context.getLogic().removeCardFromDeck(player.getId(), minionCard);
			}
		}
	}

}
