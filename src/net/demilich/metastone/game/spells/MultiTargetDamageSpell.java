package net.demilich.metastone.game.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
		Map<SpellArg, Object> arguments = SpellDesc.build(MultiTargetDamageSpell.class);
		arguments.put(SpellArg.VALUE, damage);
		arguments.put(SpellArg.HOW_MANY, targets);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int damage = desc.getValue();
		int targets = desc.getInt(SpellArg.HOW_MANY, 2);
		List<Minion> validTargets = new ArrayList<>(context.getOpponent(player).getMinions());
		for (int i = 0; i < targets; i++) {
			int randomIndex = ThreadLocalRandom.current().nextInt(validTargets.size());
			Actor randomTarget = validTargets.remove(randomIndex);
			context.getLogic().damage(player, randomTarget, damage, source);
		}
	}

}
