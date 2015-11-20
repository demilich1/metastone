package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class RecastSpell extends Spell {
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Card card = (Card) desc.get(SpellArg.CARD);
		if (desc.get(SpellArg.CARD).toString().equals("PENDING_CARD")) {
			card = (Card) context.getEnvironment().get(Environment.PENDING_CARD);
		}
		if (card == null) {
			return;
		}
		if (card instanceof SpellCard) {
			SpellCard spell = (SpellCard) card;
			SpellUtils.castChildSpell(context, player, spell.getSpell(), source, target);
		}
	}

}
