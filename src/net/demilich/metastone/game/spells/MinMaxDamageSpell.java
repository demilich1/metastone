package net.demilich.metastone.game.spells;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class MinMaxDamageSpell extends DamageSpell {
	
	public static SpellDesc create(EntityReference target, int minDamage, int maxDamage, boolean randomTarget) {
		Map<SpellArg, Object> arguments = SpellDesc.build(MinMaxDamageSpell.class);
		arguments.put(SpellArg.MIN_DAMAGE, minDamage);
		arguments.put(SpellArg.MAX_DAMAGE, maxDamage);
		arguments.put(SpellArg.TARGET, target);
		arguments.put(SpellArg.RANDOM_TARGET, randomTarget);
		return new SpellDesc(arguments);
	}
	
	public static SpellDesc create(int minDamage, int maxDamage) {
		return create(null, minDamage, maxDamage, false);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int minDamage = desc.getInt(SpellArg.MIN_DAMAGE, 0);
		int maxDamage = desc.getInt(SpellArg.MAX_DAMAGE, 0);
		int damageRange = maxDamage - minDamage;
		int damageRoll = minDamage + ThreadLocalRandom.current().nextInt(damageRange + 1);

		context.getLogic().damage(player, (Actor)target, damageRoll, source);
	}

}
