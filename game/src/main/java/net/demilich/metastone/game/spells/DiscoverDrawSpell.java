package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class DiscoverDrawSpell extends Spell {
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		CardCollection cards = new CardCollection();
		
		if (player.getDeck().isEmpty()) {
		  return;
		}
		
		int count = desc.getValue(SpellArg.HOW_MANY, context, player, target, source, 3);
		for (int i = 0; i < count; i++) {
			if (!player.getDeck().isEmpty()) {
				Card card = player.getDeck().peekFirst();
				context.getLogic().removeCardFromDeck(player.getId(), card);
				cards.add(card);
			}
		}
		
		if (!cards.isEmpty()) {
			SpellUtils.castChildSpell(context, player, SpellUtils.getDiscover(context, player, desc, cards).getSpell(), source, target);
		}
	}

}
