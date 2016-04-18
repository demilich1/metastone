package net.demilich.metastone.game.spells;

import java.util.Map;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class AuraBuffSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(AuraBuffSpell.class);

	public static SpellDesc create(int attackBonus) {
		return create(attackBonus, 0);
	}

	public static SpellDesc create(int attackBonus, int hpBonus) {
		return create(attackBonus, hpBonus, null);
	}

	public static SpellDesc create(int attackBonus, int hpBonus, Predicate<Entity> targetFilter) {
		Map<SpellArg, Object> arguments = SpellDesc.build(AuraBuffSpell.class);
		arguments.put(SpellArg.ATTACK_BONUS, attackBonus);
		arguments.put(SpellArg.HP_BONUS, hpBonus);
		if (targetFilter != null) {
			arguments.put(SpellArg.FILTER, targetFilter);
		}

		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int attackBonus = desc.getValue(SpellArg.ATTACK_BONUS, context, player, target, source, 0);
		int hpBonus = desc.getValue(SpellArg.HP_BONUS, context, player, target, source, 0);
		Actor targetActor = (Actor) target;
		logger.debug("{} gains ({} from aura effect)", targetActor, attackBonus + "/" + hpBonus);
		if (attackBonus != 0) {
			targetActor.modifyAttribute(Attribute.AURA_ATTACK_BONUS, attackBonus);
		}
		if (hpBonus != 0) {
			targetActor.modifyAuraHpBonus(hpBonus);
		}

	}
}
