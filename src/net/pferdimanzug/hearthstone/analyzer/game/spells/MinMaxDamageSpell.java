package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
