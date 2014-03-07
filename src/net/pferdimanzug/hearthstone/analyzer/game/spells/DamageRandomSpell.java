package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;

public class DamageRandomSpell extends DamageSpell {

	private final int iterations;

	public DamageRandomSpell(int damage, int iterations) {
		super(damage);
		this.iterations = iterations;
	}

	@Override
	public void cast(GameContext context, Player player, List<Actor> targets) {
		int missiles = iterations;
		if (applySpellpower) {
			missiles += context.getLogic().getTotalTagValue(player, GameTag.SPELL_POWER);
		}
		for (int i = 0; i < missiles; i++) {
			List<Actor> validTargets = getValidTargets(targets);
			Actor randomTarget = validTargets.get(ThreadLocalRandom.current().nextInt(validTargets.size()));
			context.getLogic().damage(player, randomTarget, getDamage(), false);
		}
	}

	private List<Actor> getValidTargets(List<Actor> targets) {
		List<Actor> validTargets = new ArrayList<Actor>();
		for (Actor actor : targets) {
			if (!actor.isDead() || actor.getEntityType() == EntityType.HERO) {
				validTargets.add(actor);
			}

		}
		return validTargets;
	}

	@Override
	protected void onCast(GameContext context, Player player, Actor target) {
	}

}
