package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.ISpellConditionChecker;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.condition.Condition;

public class ConditionalEffectSpell extends Spell {

	public static SpellDesc create(SpellDesc base, SpellDesc additional, ISpellConditionChecker condition) {
		Map<SpellArg, Object> arguments = SpellDesc.build(ConditionalEffectSpell.class);
		arguments.put(SpellArg.SPELL_1, base);
		arguments.put(SpellArg.SPELL_2, additional);
		arguments.put(SpellArg.SPELL_CONDITION_CHECKER, condition);
		return new SpellDesc(arguments);
	}

	protected boolean isConditionFulfilled(GameContext context, Player player, SpellDesc desc, Entity target) {
		Condition condition = (Condition) desc.get(SpellArg.CONDITION);
		return condition.isFulfilled(context, player, target);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		boolean exclusive = desc.getBool(SpellArg.EXCLUSIVE);
		SpellDesc primarySpell = (SpellDesc) desc.get(SpellArg.SPELL_1);
		SpellDesc secondarySpell = (SpellDesc) desc.get(SpellArg.SPELL_2);

		if (exclusive) {
			SpellUtils.castChildSpell(context, player, isConditionFulfilled(context, player, desc, target) ? secondarySpell : primarySpell, source, target);
		} else {
			SpellUtils.castChildSpell(context, player, primarySpell, source, target);
			if (isConditionFulfilled(context, player, desc, target)) {
				SpellUtils.castChildSpell(context, player, secondarySpell, source, target);
			}
		}

	}

}
