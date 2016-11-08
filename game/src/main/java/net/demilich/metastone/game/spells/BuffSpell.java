package net.demilich.metastone.game.spells;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class BuffSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(BuffSpell.class);

	public static SpellDesc create(EntityReference target, int value) {
		Map<SpellArg, Object> arguments = SpellDesc.build(BuffSpell.class);
		arguments.put(SpellArg.VALUE, value);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(EntityReference target, int attackBonus, int hpBonus) {
		Map<SpellArg, Object> arguments = SpellDesc.build(BuffSpell.class);
		arguments.put(SpellArg.ATTACK_BONUS, attackBonus);
		arguments.put(SpellArg.HP_BONUS, hpBonus);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int attackBonus = desc.getValue(SpellArg.ATTACK_BONUS, context, player, target, source, 0);
		int hpBonus = desc.getValue(SpellArg.HP_BONUS, context, player, target, source, 0);
		int value = desc.getValue(SpellArg.VALUE, context, player, target, source, 0);
		
		if (value != 0) {
			attackBonus = hpBonus = value;
		}
		logger.debug("{} gains ({})", target, attackBonus + "/" + hpBonus);

		if (attackBonus != 0) {
			target.modifyAttribute(Attribute.ATTACK_BONUS, attackBonus);
		}
		if (hpBonus != 0) {
			target.modifyHpBonus(hpBonus);
		}
	}

}
