package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class JoustSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		if (!context.getLogic().joust(player)) {
			return;
		}
		
		SpellDesc spell = (SpellDesc) desc.get(SpellArg.SPELL);
		SpellUtils.castChildSpell(context, player, spell, source, target);
	}

}
