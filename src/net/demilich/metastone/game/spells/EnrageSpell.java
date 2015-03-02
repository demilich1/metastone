package net.demilich.metastone.game.spells;

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
		SpellDesc desc = new SpellDesc(EnrageSpell.class);
		desc.setValue(attackBonus);
		desc.set(SpellArg.GAME_TAG, tag);
		desc.setTarget(EntityReference.SELF);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int attackBonus = desc.getValue();
		boolean enraged = target.hasStatus(GameTag.ENRAGED);
		target.setTag(GameTag.CONDITIONAL_ATTACK_BONUS, enraged ? attackBonus : 0);
		GameTag tag = (GameTag) desc.get(SpellArg.GAME_TAG);
		if (tag != null) {
			if (enraged) {
				context.getLogic().applyTag(target, tag);
			} else {
				context.getLogic().removeTag(target, tag);
			}
		}
	}

}
