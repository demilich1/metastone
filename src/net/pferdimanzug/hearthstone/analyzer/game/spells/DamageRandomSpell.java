package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;

public class DamageRandomSpell extends DamageSpell {

	private final int iterations;

	public DamageRandomSpell(int damage, int iterations) {
		super(damage);
		this.iterations = iterations;
	}
	
	@Override
	public void cast(GameContext context, Player player, List<Actor> targets) {
		int missiles = iterations + context.getLogic().getTotalTagValue(player, GameTag.SPELL_POWER);
		for (int i = 0; i < missiles; i++) {
			Actor randomTarget = null;
			while (randomTarget == null || randomTarget.isDead()) {
				randomTarget = targets.get(ThreadLocalRandom.current().nextInt(targets.size()));
			}
			
			context.getLogic().damage(player, randomTarget, getDamage(), false);
		}
	}

	@Override
	protected void onCast(GameContext context, Player player, Actor target) {
	}

}
