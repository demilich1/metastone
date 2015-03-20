package net.demilich.metastone.game.spells;

import java.util.Map;
import java.util.function.Predicate;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuffSpell extends Spell {

	public static SpellDesc create(int attackBonus) {
		return create(null, attackBonus, 0, false);
	}
	
	public static SpellDesc create(int attackBonus, int hpBonus) {
		return create(null, attackBonus, hpBonus);
	}
	
	public static SpellDesc create(EntityReference target, int attackBonus, int hpBonus) {
		return create(target, attackBonus, hpBonus, false);
	}
	
	public static SpellDesc create(EntityReference target, int attackBonus, int hpBonus, boolean randomTarget) {
		return create(target, attackBonus, hpBonus, null, randomTarget);
	}

	public static SpellDesc create(EntityReference target, int attackBonus, int hpBonus, Predicate<Entity> targetFilter, boolean randomTarget) {
		Map<SpellArg, Object> arguments = SpellDesc.build(BuffSpell.class);
		arguments.put(SpellArg.ATTACK_BONUS, attackBonus);
		arguments.put(SpellArg.HP_BONUS, hpBonus);
		arguments.put(SpellArg.TARGET, target);
		arguments.put(SpellArg.RANDOM_TARGET, randomTarget);
		if (targetFilter != null) {
			arguments.put(SpellArg.ENTITY_FILTER, targetFilter);
		}
		return new SpellDesc(arguments);
	}
	
	public static SpellDesc create(IValueProvider attackValueProvider, IValueProvider hpValueProvider) {
		return create(null, attackValueProvider, hpValueProvider);
	}

	public static SpellDesc create(EntityReference target, IValueProvider attackValueProvider, IValueProvider hpValueProvider) {
		Map<SpellArg, Object> arguments = SpellDesc.build(BuffSpell.class);
		arguments.put(SpellArg.VALUE_PROVIDER, attackValueProvider);
		arguments.put(SpellArg.SECOND_VALUE_PROVIDER, hpValueProvider);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	private static Logger logger = LoggerFactory.getLogger(BuffSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int attackBonus = desc.getInt(SpellArg.ATTACK_BONUS);
		int hpBonus = desc.getInt(SpellArg.HP_BONUS);

		IValueProvider attackValueProvider = (IValueProvider) desc.get(SpellArg.VALUE_PROVIDER);
		IValueProvider hpValueProvider = (IValueProvider) desc.get(SpellArg.SECOND_VALUE_PROVIDER);

		if (attackValueProvider != null) {
			attackBonus = attackValueProvider.provideValue(context, player, target);
		}
		if (hpValueProvider != null) {
			hpBonus = hpValueProvider.provideValue(context, player, target);
		}

		logger.debug("{} gains ({})", target, attackBonus + "/" + hpBonus);

		Actor targetActor = (Actor) target;

		if (attackBonus != 0) {
			targetActor.modifyTag(GameTag.ATTACK_BONUS, +attackBonus);
		}
		if (hpBonus != 0) {
			targetActor.modifyHpBonus(+hpBonus);
		}
	}

}
