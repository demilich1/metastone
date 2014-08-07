package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class HealingSpell extends Spell {
	
	public static SpellDesc create(int healing) {
		SpellDesc desc = new SpellDesc(HealingSpell.class);
		desc.set(SpellArg.HEALING, healing);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int healing = desc.getInt(SpellArg.HEALING);
		context.getLogic().heal(player, (Actor) target, healing, desc.getSource());
	}

}
