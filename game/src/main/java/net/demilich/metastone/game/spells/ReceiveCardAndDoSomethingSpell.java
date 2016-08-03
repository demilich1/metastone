package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.CardLocation;

public class ReceiveCardAndDoSomethingSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		for (Card card : SpellUtils.getCards(desc)) {
			context.getLogic().receiveCard(player.getId(), card);
			// card may be null (i.e. try to draw from deck, but already in
			// fatigue)
			if (card == null || card.getLocation() == CardLocation.GRAVEYARD) {
				return;
			}
			SpellDesc cardEffectSpell = (SpellDesc) desc.get(SpellArg.SPELL);
			context.setEventCard(card);
			SpellUtils.castChildSpell(context, player, cardEffectSpell, source, card);
			context.setEventCard(null);
		}
	}

}
