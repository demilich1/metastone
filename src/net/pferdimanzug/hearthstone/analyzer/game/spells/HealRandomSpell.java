package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class HealRandomSpell extends HealingSpell {
	
	public static SpellDesc create(int healing) {
		SpellDesc desc = new SpellDesc(HealRandomSpell.class);
		desc.set(SpellArg.HEALING, healing);
		return desc;
	}

	@Override
	public void cast(GameContext context, Player player, SpellDesc desc, List<Entity> targets) {
		List<Entity> validTargets = new ArrayList<Entity>();
		for (Entity entity : targets) {
			Actor actor = (Actor) entity;
			if (actor.isWounded()) {
				validTargets.add(actor);
			}
		}

		if (validTargets.isEmpty()) {
			return;
		}

		super.onCast(context, player, desc, SpellUtils.getRandomTarget(validTargets));

	}

}
