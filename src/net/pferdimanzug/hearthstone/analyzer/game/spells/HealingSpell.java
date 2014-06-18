package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class HealingSpell extends Spell {

	private final int healing;

	public HealingSpell(int healing) {
		this.healing = healing;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		context.getLogic().heal((Actor) target, healing);
	}

}
