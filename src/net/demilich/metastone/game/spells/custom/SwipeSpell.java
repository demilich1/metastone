package net.demilich.metastone.game.spells.custom;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SwipeSpell extends Spell {
	
	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(SwipeSpell.class);
		return new SpellDesc(arguments);
	}

	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int primaryDamage = desc.getInt(SpellArg.VALUE);
		int secondaryDamage = desc.getInt(SpellArg.SECONDARY_VALUE);
		for (Actor character : context.getOpponent(player).getCharacters()) {
			int damage = character == target ? primaryDamage : secondaryDamage;
			context.getLogic().damage(player, character, damage, source);
		}
	}

}
