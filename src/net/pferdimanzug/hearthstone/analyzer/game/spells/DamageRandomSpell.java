package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellSource;

public class DamageRandomSpell extends DamageSpell {
	
	public static SpellDesc create(int damage, int iterations) {
		SpellDesc desc = new SpellDesc(DamageRandomSpell.class);
		desc.set(SpellArg.DAMAGE, damage);
		desc.set(SpellArg.ITERATIONS, iterations);
		return desc;
	}

	@Override
	public void cast(GameContext context, Player player, SpellDesc desc, List<Entity> targets) {
		int missiles = desc.getInt(SpellArg.ITERATIONS);
		int damage = desc.getInt(SpellArg.DAMAGE);
		if (desc.getSource() == SpellSource.SPELL_CARD) {
			missiles = context.getLogic().applySpellpower(player, missiles);
			missiles = context.getLogic().applyAmplify(player, missiles);
		}
		for (int i = 0; i < missiles; i++) {
			List<Actor> validTargets = SpellUtils.getValidRandomTargets(targets);
			Actor randomTarget = SpellUtils.getRandomTarget(validTargets);
			context.getLogic().damage(player, randomTarget, damage, SpellSource.SPELL_TRIGGER);
		}
	}


	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
	}

}
