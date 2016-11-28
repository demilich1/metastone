package net.demilich.metastone.game.spells;

import java.util.List;
import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.condition.Condition;

public class CastRepeatedlySpell extends Spell {

	public static SpellDesc create(SpellDesc spell, int value) {
		Map<SpellArg, Object> arguments = SpellDesc.build(CastRandomSpellSpell.class);
		arguments.put(SpellArg.SPELL, spell);
		arguments.put(SpellArg.HOW_MANY, value);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		
		int iterations = desc.getValue(SpellArg.HOW_MANY, context, player, target, source, 1);
		SpellDesc spell = (SpellDesc) desc.get(SpellArg.SPELL);
		Condition condition = (Condition) desc.get(SpellArg.CONDITION);
		
		for (int i = 0; i < iterations; i++) {
			if (target == null) {
				SpellUtils.castChildSpell(context, player, spell, source, null);
				if (condition != null && condition.isFulfilled(context, player, source, null)) {
					return;
				}
			} else {
				List<Entity> targets = context.resolveTarget(player, source, desc.getTarget());
				if (targets.isEmpty()) {
					return;
				}
				Entity randomTarget = SpellUtils.getRandomTarget(targets);
				SpellUtils.castChildSpell(context, player, spell, source, randomTarget);
				if (condition != null && condition.isFulfilled(context, player, source, randomTarget)) {
					return;
				}
			}

		}
	}

}
