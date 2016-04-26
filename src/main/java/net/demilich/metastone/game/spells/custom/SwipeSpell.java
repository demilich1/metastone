package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SwipeSpell extends Spell {

	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int primaryDamage = desc.getValue(SpellArg.VALUE, context, player, target, source, 2);
		int secondaryDamage = desc.getValue(SpellArg.SECONDARY_VALUE, context, player, target, source, 1);
		for (Actor character : context.getOpponent(player).getCharacters()) {
			int damage = character == target ? primaryDamage : secondaryDamage;
			context.getLogic().damage(player, character, damage, source);
		}
	}

}
