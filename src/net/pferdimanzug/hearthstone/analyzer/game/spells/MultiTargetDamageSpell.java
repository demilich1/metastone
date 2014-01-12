package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class MultiTargetDamageSpell extends Spell {

	private int targets;
	private int damage;

	public MultiTargetDamageSpell(int damage, int targets) {
		this.damage = damage;
		this.targets = targets;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		List<Minion> validTargets = new ArrayList<>(context.getOpponent(player).getMinions());
		for (int i = 0; i < targets; i++) {
			int randomIndex = ThreadLocalRandom.current().nextInt(validTargets.size());
			Entity randomTarget = validTargets.remove(randomIndex);
			context.getLogic().damage(randomTarget, damage);
		}
	}

}
