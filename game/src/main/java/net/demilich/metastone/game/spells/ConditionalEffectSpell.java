package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.condition.Condition;

public class ConditionalEffectSpell extends Spell {

	protected boolean isConditionFulfilled(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Condition condition = (Condition) desc.get(SpellArg.CONDITION);
		return condition.isFulfilled(context, player, source, target);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		boolean exclusive = desc.getBool(SpellArg.EXCLUSIVE);
		SpellDesc primarySpell = (SpellDesc) desc.get(SpellArg.SPELL_1);
		SpellDesc secondarySpell = (SpellDesc) desc.get(SpellArg.SPELL_2);

		if (exclusive) {
			SpellUtils.castChildSpell(context, player, isConditionFulfilled(context, player, desc, source, target) ? secondarySpell : primarySpell,
					source, target);
		} else {
			SpellUtils.castChildSpell(context, player, primarySpell, source, target);
			if (isConditionFulfilled(context, player, desc, source, target)) {
				SpellUtils.castChildSpell(context, player, secondarySpell, source, target);
			}
		}

	}

}
