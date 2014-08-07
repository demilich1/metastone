package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
		spell.setSource(wrapper.getSource());
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
