package net.demilich.metastone.game.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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
			Entity source = context.resolveSingleTarget(desc.getSourceEntity());
			context.getLogic().damage(player, randomTarget, damage, source);
		}
	}

}
