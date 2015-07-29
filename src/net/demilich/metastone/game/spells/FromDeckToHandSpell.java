package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class FromDeckToHandSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Race race = (Race) desc.get(SpellArg.RACE);
		int value = desc.getValue();
		String replacementCard = (String) desc.get(SpellArg.CARD);

		CardCollection relevantCards = SpellUtils.getCards(player.getDeck(), card -> card.getAttribute(Attribute.RACE) == race);
		for (int i = 0; i < value; i++) {
			Card card = null;
			if (!relevantCards.isEmpty()) {
				card = relevantCards.getRandom();
				relevantCards.remove(card);
				player.getDeck().remove(card);
			} else if (replacementCard != null) {
				card = CardCatalogue.getCardById(replacementCard);
			}

			if (card != null) {
				context.getLogic().receiveCard(player.getId(), card);
			}
		}
	}

}
