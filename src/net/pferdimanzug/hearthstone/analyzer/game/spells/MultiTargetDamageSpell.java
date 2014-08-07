package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class MultiTargetDamageSpell extends DamageSpell {

	public static SpellDesc create(int damage, int targets) {
		SpellDesc desc = new SpellDesc(MultiTargetDamageSpell.class);
		desc.set(SpellArg.DAMAGE, damage);
		desc.set(SpellArg.ITERATIONS, targets);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int damage = desc.getInt(SpellArg.DAMAGE);
		int targets = desc.getInt(SpellArg.ITERATIONS);
		List<Minion> validTargets = new ArrayList<>(context.getOpponent(player).getMinions());
		for (int i = 0; i < targets; i++) {
			int randomIndex = ThreadLocalRandom.current().nextInt(validTargets.size());
			Actor randomTarget = validTargets.remove(randomIndex);
			context.getLogic().damage(player, randomTarget, damage, desc.getSource());
		}
	}

}
