package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class ModifyMaxManaSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int value = desc.getValue(SpellArg.VALUE, context, player, target, source, 0);
		boolean fullManaCrystals = desc.getBool(SpellArg.FULL_MANA_CRYSTALS);
		
		context.getLogic().modifyMaxMana(player, value);
		if (fullManaCrystals) {
			context.getLogic().modifyCurrentMana(player.getId(), value);
		}
	}

}
