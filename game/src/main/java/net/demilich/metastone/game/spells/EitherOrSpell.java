package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.ISpellConditionChecker;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.condition.Condition;
import net.demilich.metastone.game.targeting.EntityReference;

public class EitherOrSpell extends Spell {

	public static SpellDesc create(EntityReference target, SpellDesc either, SpellDesc or, ISpellConditionChecker condition) {
		Map<SpellArg, Object> arguments = SpellDesc.build(EitherOrSpell.class);
		arguments.put(SpellArg.SPELL_1, either);
		arguments.put(SpellArg.SPELL_2, or);
		arguments.put(SpellArg.CONDITION, condition);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(SpellDesc either, SpellDesc or, ISpellConditionChecker condition) {
		return create(null, either, or, condition);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Condition condition = (Condition) desc.get(SpellArg.CONDITION);
		SpellDesc either = (SpellDesc) desc.get(SpellArg.SPELL_1);
		SpellDesc or = (SpellDesc) desc.get(SpellArg.SPELL_2);

		SpellDesc spellToCast = condition.isFulfilled(context, player, source, target) ? either : or;
		SpellUtils.castChildSpell(context, player, spellToCast, source, target);
	}

}
