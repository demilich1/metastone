package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.utils.MathUtils;

public class HealToFullSpell extends Spell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(HealToFullSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Actor actor = (Actor) target;
		int missingHp = MathUtils.clamp(actor.getMaxHp() - actor.getHp(), 0, 9999);
		Entity source = context.resolveSingleTarget(desc.getSourceEntity());
		context.getLogic().heal(player, actor, missingHp, source);
	}

}
