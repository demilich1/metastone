package net.demilich.metastone.game.spells;

import java.util.Map;
import java.util.function.Predicate;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProvider;
import net.demilich.metastone.game.targeting.EntityReference;

public class DamageSpell extends Spell {

	public static SpellDesc create(EntityReference target, int damage) {
		return create(target, damage, false);
	}

	public static SpellDesc create(EntityReference target, int damage, boolean randomTarget) {
		return create(target, damage, null, randomTarget);
	}

	public static SpellDesc create(EntityReference target, int damage, Predicate<Entity> targetFilter, boolean randomTarget) {
		Map<SpellArg, Object> arguments = SpellDesc.build(DamageSpell.class);
		arguments.put(SpellArg.VALUE, damage);
		arguments.put(SpellArg.TARGET, target);
		arguments.put(SpellArg.RANDOM_TARGET, randomTarget);
		if (targetFilter != null) {
			arguments.put(SpellArg.FILTER, targetFilter);
		}
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(EntityReference target, ValueProvider damageModfier) {
		Map<SpellArg, Object> arguments = SpellDesc.build(DamageSpell.class);
		arguments.put(SpellArg.VALUE, damageModfier);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(int damage) {
		return create(null, damage);
	}

	public static SpellDesc create(ValueProvider damageModfier) {
		return create(null, damageModfier);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int damage = 0;
		// TODO Rewrite to more accurate way to grab Damage Stack damage.
		if (!desc.contains(SpellArg.VALUE) && !context.getDamageStack().isEmpty()) {
			damage = context.getDamageStack().peek();
		} else {
			damage = desc.getValue(SpellArg.VALUE, context, player, target, source, 0);
		}
		
		boolean ignoreSpellDamage = desc.getBool(SpellArg.IGNORE_SPELL_DAMAGE);
		context.getLogic().damage(player, (Actor) target, damage, source, ignoreSpellDamage);
	}

}
