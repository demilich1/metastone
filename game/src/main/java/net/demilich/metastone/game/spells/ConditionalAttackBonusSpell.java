package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProvider;
import net.demilich.metastone.game.targeting.EntityReference;

public class ConditionalAttackBonusSpell extends Spell {

	public static SpellDesc create(EntityReference target, ValueProvider valueProvider) {
		Map<SpellArg, Object> arguments = SpellDesc.build(ConditionalAttackBonusSpell.class);
		arguments.put(SpellArg.VALUE, valueProvider);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(ValueProvider valueProvider) {
		return create(null, valueProvider);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int attackBonus = desc.getValue(SpellArg.VALUE, context, player, target, source, 0);
		target.setAttribute(Attribute.CONDITIONAL_ATTACK_BONUS, attackBonus);
	}
}
