package net.demilich.metastone.game.spells;

import java.util.concurrent.ThreadLocalRandom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class MinMaxDamageSpell extends DamageSpell {
	
	public static SpellDesc create(int minDamage, int maxDamage) {
		SpellDesc desc = new SpellDesc(MinMaxDamageSpell.class);
		desc.set(SpellArg.MIN_DAMAGE, minDamage);
		desc.set(SpellArg.MAX_DAMAGE, maxDamage);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int minDamage = desc.getInt(SpellArg.MIN_DAMAGE);
		int maxDamage = desc.getInt(SpellArg.MAX_DAMAGE);
		int damageRange = maxDamage - minDamage;
		int damageRoll = minDamage + ThreadLocalRandom.current().nextInt(damageRange + 1);
		desc.set(SpellArg.DAMAGE, damageRoll);
		super.onCast(context, player, desc, target);
	}

}
