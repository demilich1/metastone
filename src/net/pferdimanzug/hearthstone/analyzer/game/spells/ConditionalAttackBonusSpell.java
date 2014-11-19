package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class ConditionalAttackBonusSpell extends Spell {
	
	public static SpellDesc create(IValueProvider valueProvider) {
		SpellDesc desc = new SpellDesc(ConditionalAttackBonusSpell.class);
		desc.set(SpellArg.VALUE_PROVIDER, valueProvider);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		IValueProvider valueProvider = (IValueProvider) desc.get(SpellArg.VALUE_PROVIDER);
		int attackBonus = valueProvider.provideValue(context, player, target);
		target.setTag(GameTag.CONDITIONAL_ATTACK_BONUS, attackBonus);
	}
}
