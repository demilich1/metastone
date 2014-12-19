package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
