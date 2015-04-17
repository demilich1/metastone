package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.ISpellConditionChecker;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ConditionalEffectSpell extends Spell {

	public static SpellDesc create(SpellDesc base, SpellDesc additional, ISpellConditionChecker condition) {
		Map<SpellArg, Object> arguments = SpellDesc.build(ConditionalEffectSpell.class);
		arguments.put(SpellArg.SPELL_1, base);
		arguments.put(SpellArg.SPELL_2, additional);
		arguments.put(SpellArg.SPELL_CONDITION_CHECKER, condition);
		return new SpellDesc(arguments);
	}

	private void castSpell(GameContext context, int playerId, SpellDesc spell, Entity target, EntityReference sourceReference) {
		EntityReference targetReference = spell.getTarget();
		if (targetReference == null && target != null) {
			targetReference = target.getReference();
		}
		context.getLogic().castSpell(playerId, spell, sourceReference, targetReference);
	}

	protected ISpellConditionChecker getCondition(SpellDesc desc) {
		return (ISpellConditionChecker) desc.get(SpellArg.SPELL_CONDITION_CHECKER);
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		ISpellConditionChecker condition = getCondition(desc);
		SpellDesc base = (SpellDesc) desc.get(SpellArg.SPELL_1);
		SpellDesc additional = (SpellDesc) desc.get(SpellArg.SPELL_2);

		EntityReference sourceReference = source != null ? source.getReference() : null;

		castSpell(context, player.getId(), base, target, sourceReference);
		if (condition.isFulfilled(context, player, target)) {
			castSpell(context, player.getId(), additional, target, sourceReference);
		}

	}

}
