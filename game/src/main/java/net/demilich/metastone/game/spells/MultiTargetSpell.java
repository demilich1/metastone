package net.demilich.metastone.game.spells;

import java.util.List;
import java.util.Map;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;

public class MultiTargetSpell extends Spell {

	public static SpellDesc create(int targets) {
		Map<SpellArg, Object> arguments = SpellDesc.build(MultiTargetSpell.class);
		arguments.put(SpellArg.VALUE, targets);
		return new SpellDesc(arguments);
	}

	@Override
	public void cast(GameContext context, Player player, SpellDesc desc, Entity source, List<Entity> targets) {
		int number = desc.getValue(SpellArg.VALUE, context, player, null, source, 1);
		SpellDesc spell = (SpellDesc) desc.get(SpellArg.SPELL);
		EntityFilter filter = (EntityFilter) desc.get(SpellArg.FILTER);
		for (int i = 0; i < number; i++) {
			List<Actor> validTargets = SpellUtils.getValidRandomTargets(SpellUtils.getValidTargets(context, player, targets, filter));
			
			if (validTargets.isEmpty()) {
				return;
			}
			Actor randomTarget = SpellUtils.getRandomTarget(validTargets);
			validTargets.remove(randomTarget);
			SpellUtils.castChildSpell(context, player, spell, source, randomTarget);
		}
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
	}

}
