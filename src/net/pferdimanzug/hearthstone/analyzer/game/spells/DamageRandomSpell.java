package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;

public class DamageRandomSpell extends DamageSpell {

	private final int iterations;

	public DamageRandomSpell(int damage, int iterations) {
		super(damage);
		this.iterations = iterations;
	}

	@Override
	public void cast(GameContext context, Player player, List<Entity> targets) {
		int missiles = iterations;
		if (getSource() == SpellSource.SPELL_CARD) {
			missiles = context.getLogic().applySpellpower(player, missiles);
			missiles = context.getLogic().applyAmplify(player, missiles);
		}
		for (int i = 0; i < missiles; i++) {
			List<Actor> validTargets = getValidTargets(targets);
			Actor randomTarget = SpellUtils.getRandomTarget(validTargets);
			context.getLogic().damage(player, randomTarget, getDamage(), SpellSource.SPELL_TRIGGER);
		}
	}

	private List<Actor> getValidTargets(List<Entity> targets) {
		List<Actor> validTargets = new ArrayList<Actor>();
		for (Entity entity : targets) {
			Actor actor = (Actor) entity;
			if (!actor.isDead() || actor.getEntityType() == EntityType.HERO) {
				validTargets.add(actor);
			}

		}
		return validTargets;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
	}

}
