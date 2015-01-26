package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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
