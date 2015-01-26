package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SwipeSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(SwipeSpell.class);
		return desc;
	}

	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int primaryDamage = 4;
		int secondaryDamage = 1;
		Entity source = context.resolveSingleTarget(desc.getSourceEntity());
		for (Actor character : context.getOpponent(player).getCharacters()) {
			int damage = character == target ? primaryDamage : secondaryDamage;
			context.getLogic().damage(player, character, damage, source);
		}
	}

}
