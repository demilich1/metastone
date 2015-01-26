package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class EitherOrSpell extends Spell {

	public static SpellDesc create(SpellDesc either, SpellDesc or, ISpellConditionChecker condition) {
		SpellDesc desc = new SpellDesc(EitherOrSpell.class);
		desc.set(SpellArg.SPELL_1, either);
		desc.set(SpellArg.SPELL_2, or);
		desc.set(SpellArg.SPELL_CONDITION_CHECKER, condition);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		ISpellConditionChecker condition = (ISpellConditionChecker) desc.get(SpellArg.SPELL_CONDITION_CHECKER);
		SpellDesc either = (SpellDesc) desc.get(SpellArg.SPELL_1);
		SpellDesc or = (SpellDesc) desc.get(SpellArg.SPELL_2);
		
		SpellDesc spellToCast = condition.isFulfilled(context, player, target) ? either : or;

		if (!spellToCast.hasPredefinedTarget()) {
			spellToCast.setTarget(desc.getTarget());
		}
		spellToCast.setSourceEntity(desc.getSourceEntity());
		context.getLogic().castSpell(player.getId(), spellToCast);
	}

	

}
