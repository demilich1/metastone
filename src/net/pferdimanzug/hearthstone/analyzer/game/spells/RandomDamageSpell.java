package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class RandomDamageSpell extends DamageSpell {

	private final int iterations;

	public RandomDamageSpell(int damage, int iterations) {
		super(damage);
		this.iterations = iterations;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		Player opponent = context.getOpponent(player);
		for (int i = 0; i < iterations; i++) {
			doDamage(context, opponent, getDamage());
		}
	}
	
	private void doDamage(GameContext context, Player opponent, int damage) {
		List<Entity> enemyCharacters = opponent.getCharacters();
		Entity randomTarget = enemyCharacters.get(ThreadLocalRandom.current().nextInt(enemyCharacters.size()));
		context.getLogic().damage(randomTarget, damage);
	}

}
