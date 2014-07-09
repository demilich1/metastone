package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class HealRandomSpell extends HealingSpell {

	public HealRandomSpell(int healing) {
		super(healing);
	}

	@Override
	public void cast(GameContext context, Player player, List<Entity> targets) {
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

		super.onCast(context, player, getRandomTarget(validTargets));

	}

}
