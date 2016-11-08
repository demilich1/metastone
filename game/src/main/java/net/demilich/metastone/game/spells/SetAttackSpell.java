package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SetAttackSpell extends Spell {

	public static SpellDesc create(int value) {
		Map<SpellArg, Object> arguments = SpellDesc.build(SetAttackSpell.class);
		arguments.put(SpellArg.VALUE, value);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int value = desc.getValue(SpellArg.VALUE, context, player, target, source, 0);
		target.setAttribute(Attribute.ATTACK, value);
		target.removeAttribute(Attribute.TEMPORARY_ATTACK_BONUS);
		target.removeAttribute(Attribute.ATTACK_BONUS);
		target.removeAttribute(Attribute.CONDITIONAL_ATTACK_BONUS);
	}

}