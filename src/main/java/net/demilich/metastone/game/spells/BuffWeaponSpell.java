package net.demilich.metastone.game.spells;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class BuffWeaponSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(BuffWeaponSpell.class);

	public static SpellDesc create(int attackBonus) {
		return create(attackBonus, 0);
	}

	public static SpellDesc create(int attackBonus, int durabilityBonus) {
		Map<SpellArg, Object> arguments = SpellDesc.build(BuffWeaponSpell.class);
		arguments.put(SpellArg.ATTACK_BONUS, attackBonus);
		arguments.put(SpellArg.HP_BONUS, durabilityBonus);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int damageBonus = desc.getInt(SpellArg.ATTACK_BONUS, 0);
		int durabilityBonus = desc.getInt(SpellArg.HP_BONUS, 0);

		Weapon weapon = player.getHero().getWeapon();
		if (weapon == null) {
			return;
		}

		logger.debug("{} gains ({})", weapon, damageBonus + "/" + durabilityBonus);
		if (damageBonus != 0) {
			weapon.modifyAttribute(Attribute.ATTACK, damageBonus);
		}
		if (durabilityBonus != 0) {
			context.getLogic().modifyDurability(weapon, durabilityBonus);
		}
	}

}
