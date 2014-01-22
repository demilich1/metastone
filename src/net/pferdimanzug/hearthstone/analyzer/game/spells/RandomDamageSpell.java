package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class RandomDamageSpell extends Spell {

	private final int iterations;
	private int damage;

	public RandomDamageSpell(int damage, int iterations) {
		this.damage = damage;
		this.iterations = iterations;
	}
	
	@Override
	public void cast(GameContext context, Player player, List<Entity> targets) {
		for (int i = 0; i < iterations; i++) {
			Entity randomTarget = null;
			while (randomTarget == null || randomTarget.isDead()) {
				randomTarget = targets.get(ThreadLocalRandom.current().nextInt(targets.size()));
			}
			
			context.getLogic().damage(randomTarget, damage);
		}
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		// TODO Auto-generated method stub
		
	}

}
