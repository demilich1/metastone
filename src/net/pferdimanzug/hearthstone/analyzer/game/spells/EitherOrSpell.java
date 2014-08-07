package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
		spellToCast.setSource(desc.getSource());
		spellToCast.setSourceEntity(desc.getSourceEntity());
		context.getLogic().castSpell(player.getId(), spellToCast);
	}

	

}
