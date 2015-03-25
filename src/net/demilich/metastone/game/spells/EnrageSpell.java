package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class EnrageSpell extends Spell {

	public static SpellDesc create(int attackBonus) {
		return create(attackBonus, null);
	}

	public static SpellDesc create(int attackBonus, GameTag tag) {
		Map<SpellArg, Object> arguments = SpellDesc.build(EnrageSpell.class);
		arguments.put(SpellArg.VALUE, attackBonus);
		arguments.put(SpellArg.TARGET, EntityReference.SELF);
		arguments.put(SpellArg.ATTRIBUTE, tag);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int attackBonus = desc.getValue();
		boolean enraged = target.hasStatus(GameTag.ENRAGED);
		target.setTag(GameTag.CONDITIONAL_ATTACK_BONUS, enraged ? attackBonus : 0);
		GameTag tag = (GameTag) desc.get(SpellArg.ATTRIBUTE);
		if (tag != null) {
			if (enraged) {
				context.getLogic().applyTag(target, tag);
			} else {
				context.getLogic().removeTag(target, tag);
			}
		}
	}

}
