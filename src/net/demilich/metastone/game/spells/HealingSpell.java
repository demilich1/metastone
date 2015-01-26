package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class HealingSpell extends Spell {

	public static SpellDesc create(int healing) {
		SpellDesc desc = new SpellDesc(HealingSpell.class);
		desc.set(SpellArg.HEALING, healing);
		return desc;
	}

	public static SpellDesc createWithRandomTarget(int healing) {
		SpellDesc desc = new SpellDesc(HealingSpell.class);
		desc.set(SpellArg.HEALING, healing);
		desc.set(SpellArg.RANDOM_TARGET, true);
		desc.setTargetFilter(entity -> ((Actor) entity).isWounded());
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int healing = desc.getInt(SpellArg.HEALING);
		Entity source = context.resolveSingleTarget(desc.getSourceEntity());
		context.getLogic().heal(player, (Actor) target, healing, source);
	}

}
