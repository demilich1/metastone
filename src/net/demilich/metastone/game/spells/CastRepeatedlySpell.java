package net.demilich.metastone.game.spells;

import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.condition.Condition;

public class CastRepeatedlySpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		if (target == null) {
			return;
		}
		int iterations = desc.getInt(SpellArg.HOW_MANY, 0);
		SpellDesc spell = (SpellDesc) desc.get(SpellArg.SPELL_1);
		Condition condition = (Condition) desc.get(SpellArg.CONDITION);
		for (int i = 0; i < iterations; i++) {
			List<Entity> targets = context.resolveTarget(player, null, desc.getTarget());
			if (targets.isEmpty()) {
				return;
			}
			Entity randomTarget = SpellUtils.getRandomTarget(targets);
			SpellUtils.castChildSpell(context, player, spell, source, randomTarget);
			if (condition.isFulfilled(context, player, randomTarget)) {
				return;
			}

		}
	}

}
