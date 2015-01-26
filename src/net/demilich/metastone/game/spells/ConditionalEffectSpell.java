package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class ConditionalEffectSpell extends Spell {
	
	public static SpellDesc create(SpellDesc base, SpellDesc additional, ISpellConditionChecker condition) {
		SpellDesc desc = new SpellDesc(ConditionalEffectSpell.class);
		desc.set(SpellArg.SPELL_1, base);
		desc.set(SpellArg.SPELL_2, additional);
		desc.set(SpellArg.SPELL_CONDITION_CHECKER, condition);
		return desc;
	}

	private void castSpell(GameContext context, int playerId, SpellDesc spell, SpellDesc wrapper) {
		if (!spell.hasPredefinedTarget()) {
			spell.setTarget(wrapper.getTarget());
		}
		spell.setSourceEntity(wrapper.getSourceEntity());
		context.getLogic().castSpell(playerId, spell);
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		ISpellConditionChecker condition = (ISpellConditionChecker) desc.get(SpellArg.SPELL_CONDITION_CHECKER);
		SpellDesc base = (SpellDesc) desc.get(SpellArg.SPELL_1);
		SpellDesc additional = (SpellDesc) desc.get(SpellArg.SPELL_2);
		
		castSpell(context, player.getId(), base, desc);
		if (condition.isFulfilled(context, player, target)) {
			castSpell(context, player.getId(), additional, desc);
		}
		
	}

}
