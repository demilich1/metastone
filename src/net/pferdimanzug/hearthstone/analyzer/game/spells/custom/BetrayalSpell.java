package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class BetrayalSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(BetrayalSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		for (Entity adjacentMinion : context.getAdjacentMinions(player, target.getReference())) {
			context.getLogic().fight(player, (Actor) target, (Actor) adjacentMinion);
		}
	}
	
}
