package net.demilich.metastone.game.spells;

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
		Card card = SpellUtils.getCard(context, desc);
		if (card == null) {
			return;
		}
		if (card instanceof SpellCard) {
			SpellCard spell = (SpellCard) card;
			spell.setSpell(spell.getSpell().addArg(SpellArg.FILTER, null));
			SpellUtils.castChildSpell(context, player, spell.getSpell(), source, target);
		}
	}

}
